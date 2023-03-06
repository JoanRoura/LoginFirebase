package com.example.loginfirebase.ui.navigation.training

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.loginfirebase.R
import com.example.loginfirebase.database.UserViewModel
import com.example.loginfirebase.databinding.FragmentTrainingBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class TrainingFragment : Fragment() {
    lateinit var binding: FragmentTrainingBinding
    lateinit var userViewModel: UserViewModel
    lateinit var userRecyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate<FragmentTrainingBinding>(inflater, R.layout.fragment_training, container, false);




        return binding.root
    }

}