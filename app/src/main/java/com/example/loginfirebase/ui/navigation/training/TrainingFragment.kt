package com.example.loginfirebase.ui.navigation.training

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.loginfirebase.R
import com.example.loginfirebase.databinding.FragmentTrainingBinding

class TrainingFragment : Fragment() {
    lateinit var binding: FragmentTrainingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate<FragmentTrainingBinding>(inflater, R.layout.fragment_training, container, false);


        return binding.root
    }

}