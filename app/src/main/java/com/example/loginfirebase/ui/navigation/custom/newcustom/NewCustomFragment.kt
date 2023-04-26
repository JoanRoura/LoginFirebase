package com.example.loginfirebase.ui.navigation.custom.newcustom

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loginfirebase.R
import com.example.loginfirebase.databinding.FragmentNewCustomBinding
import com.example.loginfirebase.model.CustomWorkout
import com.example.loginfirebase.model.Exercise
import com.example.loginfirebase.ui.navigation.custom.newcustom.allexercises.ExerciseAdapter
import com.example.loginfirebase.ui.navigation.custom.newcustom.allexercises.ExercisesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class NewCustomFragment : Fragment() {
    private lateinit var binding: FragmentNewCustomBinding

    private val db = Firebase.firestore

    private val exerciseViewModel: ExercisesViewModel by activityViewModels<ExercisesViewModel>()

    private lateinit var exerciseAdapter: ExerciseAdapter
    private lateinit var newCustomAdapter: NewCustomAdapter

    private var exercises: Exercise = Exercise()

    private val selectedExercises = mutableListOf<Exercise>()

    private lateinit var dialog: BottomSheetDialog

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentNewCustomBinding.inflate(inflater, container, false)

        exerciseAdapter = ExerciseAdapter()

        binding.bAddExercise.setOnClickListener {
            showBottomSheet()
        }

        binding.bSaveCustom.setOnClickListener {
            val currentCustomName = binding.etNameCustomWorkout.text.toString()
            val exercisesCustomWorkout: MutableList<String> =
                selectedExercises.map { it.id ?: "" }.toMutableList()
            val user = Firebase.auth.currentUser

            val currentDate = LocalDate.now()
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            val formattedDate = currentDate.format(formatter)

            if (user != null && currentCustomName.isNotEmpty()) {
                createCustomWorkout(currentCustomName,
                    exercisesCustomWorkout,
                    formattedDate,
                    user.uid)
            }else{
                Toast.makeText(requireContext(), "Create a Workout Name", Toast.LENGTH_SHORT).show()
            }
        }

        binding.bCancelCustom.setOnClickListener {
            goToCustomFragment()
        }

        newCustomAdapter = NewCustomAdapter(selectedExercises)

        return binding.root
    }

    private fun goToCustomFragment() {
        view?.findNavController()?.navigate(R.id.action_newCustomFragment_to_customFragment)
    }

    private fun createCustomWorkout(
        currentCustomName: String,
        exercisesCustomWorkout: MutableList<String>,
        formattedDate: String,
        uid: String,
    ) {

        if (selectedExercises.isNotEmpty()) {

            val newCustomWorkout =
                CustomWorkout(uid, currentCustomName, exercisesCustomWorkout, formattedDate)

            db.collection(getString(R.string.custom_workouts_collection))
                .document(replaceSpacesWithUnderscores(currentCustomName))
                .set(newCustomWorkout)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "Routine created", Toast.LENGTH_SHORT)
                        .show()
                    goToCustomFragment()
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(),
                        "I cant create the routine",
                        Toast.LENGTH_SHORT).show()
                }
        }

    }

    private fun showBottomSheet() {
        val dialogView = layoutInflater.inflate(R.layout.bottom_sheet_exercises, null)

        val rvExercises = dialogView.findViewById<RecyclerView>(R.id.rvAllExercises)
        rvExercises.layoutManager = LinearLayoutManager(dialogView.context)
        rvExercises.setHasFixedSize(true)
        rvExercises.adapter = exerciseAdapter

        observeCard(dialogView)

        exerciseAdapter.setItemListener(object : ExerciseAdapter.OnItemClickListener {
            override fun onItemClick(exercise: Exercise) {
//                exerciseViewModel.setExercises(exercises)
                if (selectedExercises.isEmpty()) {
                    selectedExercises.add(exercise)
                    Toast.makeText(requireContext(), "Exercice Add", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    var exerciseExists = false

                    for (selectedExercise in selectedExercises) {
                        if (selectedExercise.name == exercise.name) {
                            Toast.makeText(requireContext(), "Exercice not Add.", Toast.LENGTH_SHORT)
                                .show()
                            exerciseExists = true
                            break
                        }
                    }

                    if (!exerciseExists) {

                        selectedExercises.add(exercise)

                        Toast.makeText(requireContext(),
                            "S'ha afegit el exercici.",
                            Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                dialog.hide()
                setRecyclerView(selectedExercises)
            }
        })

        dialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
        dialog.setContentView(dialogView)
        dialog.show()
    }

    private fun observeCard(dialogView: View) {
        exerciseViewModel.getAllExercises().observe(requireActivity()) {
            exerciseAdapter.setListData(it)
            exerciseAdapter.notifyDataSetChanged()
            val fixList = exerciseAdapter.getListData()

            val searchFilter = dialogView.findViewById<EditText>(R.id.etFilter)


            searchFilter.addTextChangedListener { watcher ->

                val filteredList = fixList.filter {
                    it.name!!.lowercase().contains(watcher.toString().lowercase())
                }

                exerciseAdapter.setListData(filteredList as MutableList<Exercise>)
                exerciseAdapter.notifyDataSetChanged()

            }
        }
    }

    private fun setRecyclerView(selectedExercises: MutableList<Exercise>) {
        val cardRecyclerView = binding.rvExercisesCustomWorkout
        cardRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val selectedItemsAdapter = NewCustomAdapter(selectedExercises)
        cardRecyclerView.adapter = selectedItemsAdapter
    }

    fun replaceSpacesWithUnderscores(inputString: String): String {
        return inputString.replace(" ", "_").lowercase()
    }
}