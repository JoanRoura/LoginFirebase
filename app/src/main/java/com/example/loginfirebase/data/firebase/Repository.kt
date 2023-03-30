package com.example.loginfirebase.data.firebase

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.loginfirebase.model.CustomWorkout
import com.example.loginfirebase.model.Exercise
import com.example.loginfirebase.model.Workout
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Repository {
    val db = Firebase.firestore

    // Endpoints Workout
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

    // Endpoints exercises
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
                            doc.getString("id"),
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
                Log.i("Error", "${exception}")
            }

        return exercise
    }

    fun deleteExercise(exercise: Exercise) {

        exercise.id?.let {
            db.collection("exercises").document(it)
                .delete()
                .addOnSuccessListener { Log.d(TAG, "S'ha borrat el document") }
                .addOnFailureListener { e -> Log.w(TAG, "No s'ha pogut borrar el docuement", e) }
        }
    }

    // Endpoints Custom Workouts
    fun getCustomWorkouts() : LiveData<MutableList<CustomWorkout>> {
        val customWorkout = MutableLiveData<MutableList<CustomWorkout>>()

        db.collection("custom_workouts")
            .get()
            .addOnSuccessListener { customWorkouts ->
                val listData = mutableListOf<CustomWorkout>()

                for (doc in customWorkouts) {
                    listData.add(
                        CustomWorkout(
                            doc.getString("exercises") as MutableList<String>,
                            doc.getString("name")!!
                        )
                    )
                }
                customWorkout.value = listData
            }
        return customWorkout
    }


}