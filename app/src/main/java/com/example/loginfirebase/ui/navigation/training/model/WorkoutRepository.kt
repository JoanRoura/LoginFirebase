package com.example.loginfirebase.ui.navigation.training.model

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.loginfirebase.model.User
import com.example.loginfirebase.model.Workout
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.math.log

class WorkoutRepository {

    var db = FirebaseFirestore.getInstance()
    private lateinit var workoutArrayList: ArrayList<Workout>

    @Volatile
    private var INSTANCE: WorkoutRepository? = null

    fun getInstance(): WorkoutRepository {
        return INSTANCE ?: synchronized(this) {

            val instance = WorkoutRepository()
            INSTANCE = instance
            instance
        }
    }

    fun loadWorkouts(workoutList: MutableLiveData<List<Workout>>) {
        workoutArrayList = ArrayList<Workout>()
        db.collection("workouts")
//            .orderBy("name", com.google.firebase.firestore.Query.Direction.ASCENDING)
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    Log.w(ContentValues.TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }
                for (dc: DocumentChange in snapshots!!.documentChanges) {
                    Log.i("hola", "${dc.document.data}")

                    when (dc.type) {
                        //DocumentChange.Type.ADDED -> Log.d(TAG, "New city: ${dc.document.data}")

                        DocumentChange.Type.ADDED -> workoutArrayList.add(dc.document.toObject(Workout::class.java))
                        DocumentChange.Type.MODIFIED -> workoutArrayList.add(dc.document.toObject(Workout::class.java))
                        DocumentChange.Type.REMOVED -> workoutArrayList.add(dc.document.toObject(Workout::class.java))
                    }
                }
                workoutList.postValue(workoutArrayList)
            }
    }
}