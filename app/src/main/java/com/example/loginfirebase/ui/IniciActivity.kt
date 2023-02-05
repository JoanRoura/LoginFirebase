package com.example.loginfirebase.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.loginfirebase.R
import com.example.loginfirebase.databinding.ActivityIniciBinding
import com.example.loginfirebase.viewmodel.LoginViewModel

class IniciActivity : AppCompatActivity() {
    lateinit var binding: ActivityIniciBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIniciBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonGoToLogin.setOnClickListener {
            val intentLoginActivity = Intent(this, LoginActivity::class.java)
            startActivity(intentLoginActivity)
        }

        binding.buttonGoToRegister.setOnClickListener {
            val intentRegisterActivity = Intent(this, RegisterActivity::class.java)
            startActivity(intentRegisterActivity)
        }
    }
}
