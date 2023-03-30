package com.example.loginfirebase.ui.navigation.training.trainingexercises

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.loginfirebase.data.firebase.Repository
import com.example.loginfirebase.model.Exercise
import com.example.loginfirebase.model.Workout

class TrainingExerecisesViewModel : ViewModel() {

    private var repository = Repository()

    private var _exercises : Exercise? = null
    val exercises get() = _exercises

    fun setWorkoutExercises(exercises: Exercise) {
        _exercises = exercises
    }

    fun getExercisesDB(workout: Workout): LiveData<MutableList<Exercise>> {

        val exercise = MutableLiveData<MutableList<Exercise>>()

        repository.getExercises(workout).observeForever() {
            exercise.value = it
        }

        return exercise
    }

    fun deleteExercisesDB(exercise: Exercise) {
        repository.deleteExercise(exercise)
    }
}