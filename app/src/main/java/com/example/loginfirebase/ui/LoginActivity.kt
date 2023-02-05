package com.example.loginfirebase.ui

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.loginfirebase.R
import com.example.loginfirebase.databinding.ActivityIniciBinding
import com.example.loginfirebase.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth



class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.buttonSignup.setOnClickListener {
            val email = binding.editTextEmail.text
            val password = binding.editTextPassword.text
            if (email.isNotEmpty() && password.isNotEmpty()) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.toString(), password.toString())
                    .addOnCompleteListener { login ->
                        if (login.isSuccessful) {
                            login.result?.user?.sendEmailVerification();

                            if (login.result?.user?.isEmailVerified == true) {
                                showVerification(login.result?.user?.email ?: "", ProviderType.BASIC)
                            }

                        } else {
                            showAlert()
                        }
                    }
            }
        }

        binding.buttonLogin.setOnClickListener {
            val email = binding.editTextEmail.text
            val password = binding.editTextPassword.text
            if (email.isNotEmpty() && password.isNotEmpty()) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email.toString(), password.toString())
                    .addOnCompleteListener { login ->
                        if (login.isSuccessful) {
                            login.result?.user?.sendEmailVerification();

                            if (login.result?.user?.isEmailVerified == true) {
                                showVerification(login.result?.user?.email ?: "", ProviderType.BASIC)
                            }
                        } else {
                            showAlert()
                        }
                    }
            }
        }
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("No hi ha una conta registrada amb aquestes credencials.")
        builder.setPositiveButton("Acceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showVerification(email: String, provider: ProviderType) {
        val intentVerificationActivity = Intent(this, VerificationActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(intentVerificationActivity)
    }


}