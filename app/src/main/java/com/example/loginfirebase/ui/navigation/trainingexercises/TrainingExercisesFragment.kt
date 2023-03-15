package com.example.loginfirebase.ui.navigation.trainingexercises

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.loginfirebase.R
import com.example.loginfirebase.databinding.FragmentTrainingBinding
import com.example.loginfirebase.databinding.FragmentTrainingExercisesBinding
import com.example.loginfirebase.model.Workout
import com.example.loginfirebase.ui.navigation.training.TrainingViewModel


class TrainingExercisesFragment : Fragment() {
    private lateinit var binding: FragmentTrainingExercisesBinding
    private val trainingViewModel by activityViewModels<TrainingViewModel>()
    private lateinit var workout : Workout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTrainingExercisesBinding.inflate(inflater,container,false)

        workout = trainingViewModel.workout!!
        binding.hol.text = workout.name

        return binding.root
    }
}