package com.example.loginfirebase.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.loginfirebase.R
import com.example.loginfirebase.databinding.ActivityRegisterBinding
import com.example.loginfirebase.databinding.ActivityVerificationBinding
import com.google.firebase.auth.FirebaseAuth

enum class ProviderType {
    BASIC
}

class VerificationActivity : AppCompatActivity() {
    lateinit var binding: ActivityVerificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle: Bundle? = intent.extras
        val email: String? = bundle?.getString("email")
        val provider: String? = bundle?.getString("provider")

        setup(email ?: "", provider ?: "");
    }

    private fun setup(email: String, provider: String) {

        binding.textViewEmail.text = email
        binding.textViewProvider.text = provider

        binding.buttonSignOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }
    }
}