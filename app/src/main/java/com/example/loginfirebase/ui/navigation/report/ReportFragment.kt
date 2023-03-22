package com.example.loginfirebase.ui.navigation.report

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.loginfirebase.R
import com.example.loginfirebase.databinding.FragmentReportBinding

class ReportFragment : Fragment() {
    lateinit var binding: FragmentReportBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentReportBinding.inflate(inflater, container, false)

        binding.buttonGoToProfile.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_reportFragment_to_profileFragment)
        }

        return binding.root
    }
}