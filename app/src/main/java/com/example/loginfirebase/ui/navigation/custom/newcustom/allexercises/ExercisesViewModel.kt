package com.example.loginfirebase.ui.navigation.custom.newcustom.allexercises

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.loginfirebase.data.firebase.Repository
import com.example.loginfirebase.model.Exercise

class ExercisesViewModel: ViewModel() {

    private var repository = Repository()

    private var _exercises : Exercise? = null
    val exercises get() = _exercises

    fun setExercises(exercises: Exercise) {
        _exercises = exercises
    }

    fun getAllExercises(): LiveData<MutableList<Exercise>> {
        val exercise = MutableLiveData<MutableList<Exercise>>()

        repository.getExercises().observeForever() {
            exercise.value = it
        }

        return exercise
    }
}