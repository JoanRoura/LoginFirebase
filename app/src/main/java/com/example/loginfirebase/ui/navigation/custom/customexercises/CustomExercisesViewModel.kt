package com.example.loginfirebase.ui.navigation.custom.customexercises

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.loginfirebase.data.firebase.Repository
import com.example.loginfirebase.model.CustomWorkout
import com.example.loginfirebase.model.Exercise

class CustomExercisesViewModel: ViewModel() {

    private var repository = Repository()

    private var _exercises : Exercise? = null
    val exercises get() = _exercises

    fun setCustomExercises(exercises: Exercise) {
        _exercises = exercises
    }

    fun getExercisesCustomDB(customWorkout: CustomWorkout): LiveData<MutableList<Exercise>> {

        val exercise = MutableLiveData<MutableList<Exercise>>()

        repository.getExercisesCustomWorkout(customWorkout).observeForever() {
            exercise.value = it
        }

        return exercise
    }
}