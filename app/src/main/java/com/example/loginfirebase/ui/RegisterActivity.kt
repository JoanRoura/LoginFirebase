package com.example.loginfirebase.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.loginfirebase.R
import com.example.loginfirebase.databinding.ActivityLoginBinding
import com.example.loginfirebase.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonRegister.setOnClickListener {
            val email = binding.editTextCorreuUsuari.text
            val password = binding.editTextContrasenyaUsuari.text
            if (email.isNotEmpty() && password.isNotEmpty()) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.toString(), password.toString())
                    .addOnCompleteListener { login ->
                        if (login.isSuccessful) {
                            showVerification(login.result?.user?.email ?: "", password.toString())
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
        builder.setMessage("S'ha produit un error en la autenticacio de l'usuari.")
        builder.setPositiveButton("Acceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showVerification(email: String, password: String) {
        val intentLogin = Intent(this, LoginActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", password)
        }
        startActivity(intentLogin)
    }
}