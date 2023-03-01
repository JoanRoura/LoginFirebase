package com.example.loginfirebase.ui.signin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.loginfirebase.R
import com.example.loginfirebase.databinding.ActivityRegisterBinding
import com.example.loginfirebase.model.User
import com.example.loginfirebase.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignInActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = Firebase.firestore

        binding.buttonRegister.setOnClickListener {
            val email = binding.editTextCorreuUsuari.text
            val password = binding.editTextContrasenyaUsuari.text

            val user = User("", password.toString(), email.toString())

            if (email.isNotEmpty() && password.isNotEmpty()) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.toString(), password.toString())
                    .addOnCompleteListener { register ->
                        if (register.isSuccessful) {
                            showVerification(register.result?.user?.email ?: "", password.toString())

                            db.collection(R.string.users_collection.toString()).document(email.toString())
                                .set(user)
                                .addOnSuccessListener {
                                    Toast.makeText(applicationContext,"S'ha creat el document",Toast.LENGTH_SHORT).show()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(applicationContext,"No s'ha pogut crear el document",Toast.LENGTH_SHORT).show()
                                }
                        } else {
                            showAlert()
                        }
                    }
            }
        }

        binding.buttonToLogin.setOnClickListener(){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
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