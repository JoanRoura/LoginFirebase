package com.example.loginfirebase.ui.navigation.newcustomworkout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.loginfirebase.R
import com.example.loginfirebase.databinding.FragmentNewCustomWorkoutBinding

class NewCustomWorkoutFragment : Fragment() {
    private lateinit var binding: FragmentNewCustomWorkoutBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        return inflater.inflate(R.layout.fragment_new_custom_workout, container, false)
    }

}