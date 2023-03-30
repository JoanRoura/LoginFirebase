package com.example.loginfirebase.ui.navigation.training.trainingexercises

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
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
    private lateinit var exercises : Exercise

    private lateinit var dialog : BottomSheetDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTrainingExercisesBinding.inflate(inflater, container,false)

        workout = trainingViewModel.workout!!

        binding.tvTitleTrainingExercisesFragment.text = workout.name
        binding.tvNumberExercisesWorkout.text = workout.number_of_exercises

        trainingExercisesAdapter = TrainingExercisesAdapter( onClickDelete = { exercise -> onDeletedItem(exercise) })

        setRecyclerView()
        observeCard(workout)

        return binding.root
    }


    private fun setRecyclerView() {
        val cardRecyclerView = binding.rvExercises
        cardRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        cardRecyclerView.setHasFixedSize(true)
        cardRecyclerView.adapter = trainingExercisesAdapter

        trainingExercisesAdapter.setItemListener(object :
            TrainingExercisesAdapter.OnItemClickListener {
            override fun onItemClick(exercise: Exercise) {
                trainingExercisesViewModel.setWorkoutExercises(exercise)

                exercises = trainingExercisesViewModel.exercises!!

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
        nameExercise.text = exercises.name

        val focusAreaxercise = dialogView.findViewById<TextView>(R.id.tvFocusAreaExercise)
        focusAreaxercise.text = exercises.focus_area

        val equipmentExercise = dialogView.findViewById<TextView>(R.id.tvEquipmentExercise)
        equipmentExercise.text = exercises.equipment

        val imageExercise = dialogView.findViewById<ImageView>(R.id.ivExercise)
        Glide.with(imageExercise.context).load(exercises.image).into(imageExercise)

        // TODO: Implementar decripcio als exercisis en el firestore
        // val descriptionExercise = dialogView.findViewById<TextView>(R.id.tvDescriptionExercise)
        // descriptionExercise.text = exercise.

        dialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
        dialog.setContentView(dialogView)
        dialog.show()
    }

    private fun onDeletedItem(exercise: Exercise) {
        trainingExercisesViewModel.deleteExercisesDB(exercise)
    }

    private fun showEdit() {
        val cardExercise = layoutInflater.inflate(R.layout.card_exercise_training, null)
        val bBorrar = cardExercise.findViewById<Button>(R.id.bBorrar)

        binding.bEditWorkout.setOnClickListener {
            bBorrar.visibility = View.VISIBLE
            trainingExercisesAdapter.notifyDataSetChanged()
        }
    }
}