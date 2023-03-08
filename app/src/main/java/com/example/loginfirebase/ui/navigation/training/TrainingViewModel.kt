package com.example.loginfirebase.ui.navigation.training

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.loginfirebase.model.Workout
import com.example.loginfirebase.ui.navigation.training.model.WorkoutRepository

class TrainingViewModel : ViewModel() {

    private val repository : WorkoutRepository
    private val _workouts = MutableLiveData<List<Workout>>()
    val workouts : LiveData<List<Workout>> = _workouts


    init {

        repository = WorkoutRepository().getInstance()
        repository.loadWorkouts(_workouts)

    }
}