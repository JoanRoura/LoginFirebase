package com.example.loginfirebase.ui.navigation.training.trainingexercisesedit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.loginfirebase.databinding.FragmentTrainingExercisesEditBinding
import com.example.loginfirebase.model.Exercise
import com.example.loginfirebase.ui.navigation.training.trainingexercises.TrainingExerecisesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.example.loginfirebase.R

class TrainingExercisesEditFragment : Fragment() {
    private lateinit var binding : FragmentTrainingExercisesEditBinding
    private lateinit var exercise : Exercise
    private val trainingExercisesViewModel by activityViewModels<TrainingExerecisesViewModel>()
    private lateinit var dialog : BottomSheetDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentTrainingExercisesEditBinding.inflate(inflater, container,false)

        exercise = trainingExercisesViewModel.exercises!!



        showBottomSheet()

        return binding.root
    }

    private fun showBottomSheet() {
        val dialogView = layoutInflater.inflate(R.layout.bottom_sheet, null)
        dialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
        dialog.setContentView(dialogView)
    }

}