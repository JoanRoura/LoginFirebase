package com.example.loginfirebase.data.firebase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.loginfirebase.model.Workout
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class WorkoutRepository {
    val db = Firebase.firestore

    fun getWorkouts() : LiveData<MutableList<Workout>> {
        val workout = MutableLiveData<MutableList<Workout>>();

        db.collection("workouts")
            .get()
            .addOnSuccessListener { workouts ->
                val listData = mutableListOf<Workout>()

                for (document in workouts) {
                    listData.add(
                        Workout(
                            document.getString("name")!!,
                            document.getString("number_of_exercises")!!
                        )
                    )
                }
                workout.value = listData
            }

        return workout
    }
}