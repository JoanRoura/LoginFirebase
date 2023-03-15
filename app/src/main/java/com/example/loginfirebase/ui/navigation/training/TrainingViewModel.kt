package com.example.loginfirebase.ui.navigation.training

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.loginfirebase.data.firebase.WorkoutRepository
import com.example.loginfirebase.model.Workout

class TrainingViewModel : ViewModel() {
    private var repository = WorkoutRepository()
    private var _workout : Workout? = null
    val workout get() = _workout

    fun hol(){
        Log.i("asda","$workout")
        Log.i("mega","$_workout")
    }


    fun setWorkout(workout: Workout) {
        _workout = workout
        hol()
    }

    fun getWorkoutsDB(): LiveData<MutableList<Workout>> {

        val workout = MutableLiveData<MutableList<Workout>>()

        repository.getWorkouts().observeForever {
            workout.value = it
        }

        return workout
    }
}