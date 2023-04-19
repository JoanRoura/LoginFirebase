package com.example.loginfirebase.ui.navigation.custom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.loginfirebase.R
import com.example.loginfirebase.databinding.FragmentCustomBinding
import com.example.loginfirebase.model.CustomWorkout

class CustomFragment : Fragment() {
    private lateinit var binding: FragmentCustomBinding
    private lateinit var customAdapter: CustomAdapter
    private val customViewModel by activityViewModels<CustomViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCustomBinding.inflate(inflater, container, false)

        customAdapter = CustomAdapter()
        binding.progressBar2.visibility = View.VISIBLE
        setRecyclerView()
        observeCard()

        binding.bAddCustomWorkout.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_customFragment_to_newCustomFragment)
        }

        return binding.root
    }

    private fun setRecyclerView() {
        val cardRecyclerView = binding.rvCustomWorkouts
        cardRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        cardRecyclerView.setHasFixedSize(true)
        cardRecyclerView.adapter = customAdapter
        //                        val exercisesList = doc.get("exercises") as MutableList<String>
//    exercisesList

        customAdapter.setItemListener(object : CustomAdapter.OnItemClickListener {
            override fun onItemClick(customWorkout: CustomWorkout) {
                customViewModel.setCustomWorkout(customWorkout)
                view?.findNavController()?.navigate(R.id.action_customFragment_to_customExercisesFragment)
            }
        })
    }

    private fun observeCard() {
        customViewModel.getCustomWorkoutsDB().observe(requireActivity()) {
            customAdapter.setListData(it)
            customAdapter?.notifyDataSetChanged()
            binding.progressBar2.visibility = View.GONE
        }
    }
}