package com.example.loginfirebase.ui.navigation.custom

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.loginfirebase.data.firebase.Repository
import com.example.loginfirebase.model.CustomWorkout

class CustomViewModel : ViewModel() {

    private var repository = Repository()

    private var _customWorkout : CustomWorkout? = null
    val customWorkout get() = _customWorkout

    fun setCustomWorkout(customWorkout: CustomWorkout) {
        _customWorkout = customWorkout
    }

    fun getCustomWorkoutsDB(): LiveData<MutableList<CustomWorkout>> {
        val customWorkout = MutableLiveData<MutableList<CustomWorkout>>()

        repository.getCustomWorkouts().observeForever {
            customWorkout.value = it
        }

        return customWorkout
    }
}