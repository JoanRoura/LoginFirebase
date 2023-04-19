package com.example.loginfirebase.ui.navigation.custom.customexercises

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.loginfirebase.databinding.FragmentCustomExercisesBinding
import com.example.loginfirebase.model.CustomWorkout
import com.example.loginfirebase.model.Exercise
import com.example.loginfirebase.ui.navigation.custom.CustomViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CustomExercisesFragment : Fragment() {
    private lateinit var binding: FragmentCustomExercisesBinding

    private val customViewModel: CustomViewModel by  activityViewModels<CustomViewModel>()
    private val customExercisesViewModel: CustomExercisesViewModel by  activityViewModels<CustomExercisesViewModel>()

    private lateinit var customExercisesAdapter: CustomExercisesAdapter

    private lateinit var customWorkout: CustomWorkout
    private lateinit var exercises : Exercise

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val formattedDate = currentDate.format(formatter)

        binding = FragmentCustomExercisesBinding.inflate(inflater, container, false)

        customWorkout = customViewModel.customWorkout!!

        binding.tvNameCustomWorkout.text = customWorkout.name
        binding.tvDateCreatedCustom.text = formattedDate.toString()

        customExercisesAdapter = CustomExercisesAdapter()

        setRecyclerView()
        observeCard(customWorkout)

        return binding.root
    }

    private fun setRecyclerView() {
        val cardRecyclerView = binding.rvExercisesCustom
        cardRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        cardRecyclerView.setHasFixedSize(true)
        cardRecyclerView.adapter = customExercisesAdapter

        customExercisesAdapter.setItemListener(object :
            CustomExercisesAdapter.OnItemClickListener {
            override fun onItemClick(exercise: Exercise) {
                customExercisesViewModel.setCustomExercises(exercise)
                exercises = customExercisesViewModel.exercises!!
            }
        })
    }

    private fun observeCard(customWorkout: CustomWorkout) {
        customExercisesViewModel.getExercisesCustomDB(customWorkout).observe(requireActivity()) {
            customExercisesAdapter.setListData(it)
            customExercisesAdapter.notifyDataSetChanged()
        }
    }
}