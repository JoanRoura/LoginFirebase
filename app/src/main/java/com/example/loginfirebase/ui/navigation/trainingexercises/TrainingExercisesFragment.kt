package com.example.loginfirebase.ui.navigation.trainingexercises

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.loginfirebase.databinding.FragmentTrainingExercisesBinding
import com.example.loginfirebase.model.Exercise
import com.example.loginfirebase.model.Workout
import com.example.loginfirebase.ui.navigation.training.TrainingViewModel


class TrainingExercisesFragment : Fragment() {
    private lateinit var binding: FragmentTrainingExercisesBinding
    private val trainingViewModel by activityViewModels<TrainingViewModel>()
    private val trainingExercisesViewModel by activityViewModels<TrainingExerecisesViewModel>()
    private lateinit var workout : Workout
    private lateinit var trainingExercisesAdapter: TrainingExercisesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTrainingExercisesBinding.inflate(inflater, container,false)

        workout = trainingViewModel.workout!!

        binding.tvTitleTrainingExercisesFragment.text = workout.name
        binding.tvNumberExercisesWorkout.text = workout.number_of_exercises

        trainingExercisesAdapter = TrainingExercisesAdapter()

        setRecyclerView()
        observeCard(workout)

        return binding.root
    }

    private fun setRecyclerView() {
        val cardRecyclerView = binding.rvExercises
        cardRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        cardRecyclerView.setHasFixedSize(true)
        cardRecyclerView.adapter = trainingExercisesAdapter

        trainingExercisesAdapter.setItemListener(object : TrainingExercisesAdapter.OnItemClickListener {
            override fun onItemClick(exercise: Exercise) {
                trainingExercisesViewModel.setWorkoutExercises(exercise)
            }
        })
    }

    private fun observeCard(workout: Workout) {
        trainingExercisesViewModel.getExercisesDB(workout).observe(requireActivity()) {
            trainingExercisesAdapter.setListData(it)
            trainingExercisesAdapter.notifyDataSetChanged()
        }
    }
}