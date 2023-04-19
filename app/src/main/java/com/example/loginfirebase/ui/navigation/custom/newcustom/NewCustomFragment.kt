package com.example.loginfirebase.ui.navigation.custom.newcustom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.loginfirebase.R
import com.example.loginfirebase.databinding.FragmentNewCustomBinding
import com.example.loginfirebase.model.Exercise
import com.example.loginfirebase.ui.navigation.custom.newcustom.allexercises.ExerciseAdapter
import com.example.loginfirebase.ui.navigation.custom.newcustom.allexercises.ExercisesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog

class NewCustomFragment : Fragment() {
    private lateinit var binding:  FragmentNewCustomBinding

    private val exerciseViewModel: ExercisesViewModel by activityViewModels<ExercisesViewModel>()

    private lateinit var exerciseAdapter: ExerciseAdapter

    private lateinit var exercises : Exercise

    private lateinit var dialog : BottomSheetDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewCustomBinding.inflate(inflater, container, false)

        exerciseAdapter = ExerciseAdapter()

        binding.bAddExercise.setOnClickListener {
            showBottomSheet()
        }

        return binding.root
    }

    private fun showBottomSheet() {
        val dialogView = layoutInflater.inflate(R.layout.bottom_sheet_exercises, null)

        val rvExercises = dialogView.findViewById<RecyclerView>(R.id.rvAllExercises)
        rvExercises.layoutManager = LinearLayoutManager(dialogView.context)
        rvExercises.setHasFixedSize(true)
        rvExercises.adapter = exerciseAdapter

        exerciseAdapter.setItemListener(object :
            ExerciseAdapter.OnItemClickListener {
            override fun onItemClick(exercise: Exercise) {
                exerciseViewModel.setExercises(exercises)
            }
        })

        dialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
        dialog.setContentView(dialogView)
        observeCard()
        dialog.show()

    }

    private fun observeCard() {
        exerciseViewModel.getAllExercises().observe(requireActivity()) {
            exerciseAdapter.setListData(it)
            exerciseAdapter.notifyDataSetChanged()
        }
    }

}