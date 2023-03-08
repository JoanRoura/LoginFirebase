package com.example.loginfirebase.ui.navigation.training

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loginfirebase.R
import com.example.loginfirebase.databinding.FragmentTrainingBinding

class TrainingFragment : Fragment() {
    private lateinit var binding: FragmentTrainingBinding
    private lateinit var workoutRecyclerView: RecyclerView
    private lateinit var trainingViewModel: TrainingViewModel
    lateinit var trainingAdapter: TrainingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate<FragmentTrainingBinding>(inflater, R.layout.fragment_training, container, false);

        trainingViewModel = ViewModelProvider(this).get(TrainingViewModel::class.java)

        workoutRecyclerView = binding.rvWorkout
        workoutRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        workoutRecyclerView.setHasFixedSize(true)
        trainingAdapter = TrainingAdapter()
        workoutRecyclerView.adapter = trainingAdapter

        trainingViewModel.workouts.observe(viewLifecycleOwner, Observer {
            trainingAdapter.updateWorkoutList(it)
        })

        return binding.root
    }
}