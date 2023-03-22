package com.example.loginfirebase.data.firebase

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.loginfirebase.model.Exercise
import com.example.loginfirebase.model.Workout
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Repository {
    val db = Firebase.firestore

    fun getWorkouts() : LiveData<MutableList<Workout>> {
        val workout = MutableLiveData<MutableList<Workout>>()

        db.collection("workouts")
            .get()
            .addOnSuccessListener { workouts ->
                val listData = mutableListOf<Workout>()

                for (doc in workouts) {
                    listData.add(
                        Workout(
                            doc.getString("name")!!,
                            doc.getString("number_of_exercises")!!,
                            doc.getString("focus_area")!!,
                            doc.getString("image")!!
                        )
                    )
                }
                workout.value = listData
            }

        return workout
    }

    fun getExercises(workout: Workout) : LiveData<MutableList<Exercise>> {
        val exercise =  MutableLiveData<MutableList<Exercise>>()

        db.collection("exercises")
            .whereEqualTo("focus_area", workout.focus_area)
            .get()
            .addOnSuccessListener { exercises ->
                val listData = mutableListOf<Exercise>()

                for (doc in exercises) {
                    listData.add(
                        Exercise(
                            doc.getString("name"),
                            doc.getString("sets"),
                            doc.getString("equipment"),
                            doc.getString("focus_area"),
                            doc.getString("image")
                        )
                    )
                }
                exercise.value = listData
            }
            .addOnFailureListener  { exception ->
                Log.i("casas", "${exception}")
            }

        return exercise
    }
}