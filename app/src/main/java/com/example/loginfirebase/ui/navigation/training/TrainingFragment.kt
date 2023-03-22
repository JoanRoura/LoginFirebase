package com.example.loginfirebase.ui.navigation.training

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loginfirebase.R
import com.example.loginfirebase.databinding.FragmentTrainingBinding
import com.example.loginfirebase.model.Workout

class TrainingFragment : Fragment() {
    private lateinit var binding: FragmentTrainingBinding
    private lateinit var workoutRecyclerView: RecyclerView
    private val trainingViewModel by activityViewModels<TrainingViewModel>()
    private lateinit var trainingAdapter: TrainingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentTrainingBinding.inflate(inflater, container, false)

//        trainingViewModel = ViewModelProvider()
        trainingAdapter = TrainingAdapter()
        binding.progressBar.visibility = View.VISIBLE
        setRecyclerView()
        observeCard()

        return binding.root
    }

    private fun setRecyclerView() {
        val cardRecyclerView = binding.rvWorkout
        cardRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        cardRecyclerView.setHasFixedSize(true)
        cardRecyclerView.adapter = trainingAdapter
        trainingAdapter.setItemListener(object: TrainingAdapter.OnItemClickListener {
            override fun onItemClick(workout: Workout) {
                trainingViewModel.setWorkout(workout)
                view?.findNavController()?.navigate(R.id.action_trainingFragment_to_trainingExercisesFragment);
            }
        })

    }

    private fun observeCard() {
        trainingViewModel.getWorkoutsDB().observe(requireActivity()) {
            trainingAdapter.setListData(it)
            trainingAdapter?.notifyDataSetChanged()
            binding.progressBar.visibility = View.GONE
        }
    }
}