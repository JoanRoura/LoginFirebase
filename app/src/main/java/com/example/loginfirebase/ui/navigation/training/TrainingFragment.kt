package com.example.loginfirebase.ui.navigation.training

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loginfirebase.R
import com.example.loginfirebase.databinding.FragmentTrainingBinding
import com.example.loginfirebase.model.Workout

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



        initRecyclerView()

        return binding.root
    }


    private fun initRecyclerView() {
        workoutRecyclerView = binding.rvWorkout
        workoutRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        workoutRecyclerView.setHasFixedSize(true)
        trainingAdapter = TrainingAdapter()
        workoutRecyclerView.adapter = trainingAdapter

//        val myPostsRv = binding.rvWorkout
//        myPostsRv.layoutManager = LinearLayoutManager(requireContext())
//        myPostsRv.setHasFixedSize(true)
//        myPostsRv.adapter = trainingAdapter
//        trainingAdapter.setItemListener(object : TrainingAdapter.onItemClickListener {
//            override fun onItemClick(workout: Workout) {
//                //val userName = user.userName
//                Toast.makeText(requireContext(), workout.name, Toast.LENGTH_SHORT).show()
//            }
//        })
    }
}