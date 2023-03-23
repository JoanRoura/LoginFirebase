package com.example.loginfirebase.ui.navigation.trainingexercises

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.loginfirebase.R
import com.example.loginfirebase.databinding.FragmentTrainingExercisesBinding
import com.example.loginfirebase.model.Exercise
import com.example.loginfirebase.model.Workout
import com.example.loginfirebase.ui.navigation.training.TrainingViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog

class TrainingExercisesFragment : Fragment() {
    private lateinit var binding: FragmentTrainingExercisesBinding

    private val trainingViewModel by activityViewModels<TrainingViewModel>()
    private val trainingExercisesViewModel by activityViewModels<TrainingExerecisesViewModel>()

    private lateinit var trainingExercisesAdapter: TrainingExercisesAdapter

    private lateinit var workout : Workout
    private lateinit var exercise : Exercise

    private lateinit var dialog : BottomSheetDialog

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
        goTo()

        exercise = trainingExercisesViewModel.exercises!!

        return binding.root
    }

    private fun goTo() {
        binding.bEditWorkout.setOnClickListener {

        }
    }

    private fun setRecyclerView() {
        val cardRecyclerView = binding.rvExercises
        cardRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        cardRecyclerView.setHasFixedSize(true)
        cardRecyclerView.adapter = trainingExercisesAdapter

        trainingExercisesAdapter.setItemListener(object : TrainingExercisesAdapter.OnItemClickListener {
            override fun onItemClick(exercise: Exercise) {
                trainingExercisesViewModel.setWorkoutExercises(exercise)

                showBottomSheet()
            }
        })
    }

    private fun observeCard(workout: Workout) {
        trainingExercisesViewModel.getExercisesDB(workout).observe(requireActivity()) {
            trainingExercisesAdapter.setListData(it)
            trainingExercisesAdapter.notifyDataSetChanged()
        }
    }

    private fun showBottomSheet() {
        val dialogView = layoutInflater.inflate(R.layout.bottom_sheet, null)

        val nameExercise = dialogView.findViewById<TextView>(R.id.tvNameExercise)
        nameExercise.text = exercise.name

        val focusAreaxercise = dialogView.findViewById<TextView>(R.id.tvFocusAreaExercise)
        focusAreaxercise.text = exercise.focus_area

        val equipmentExercise = dialogView.findViewById<TextView>(R.id.tvEquipmentExercise)
        equipmentExercise.text = exercise.equipment

        // TODO: Implementar decripcio als exercisis en el firestore
        // val descriptionExercise = dialogView.findViewById<TextView>(R.id.tvDescriptionExercise)
        // descriptionExercise.text = exercise.

        dialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
        dialog.setContentView(dialogView)
        dialog.show()
    }


}