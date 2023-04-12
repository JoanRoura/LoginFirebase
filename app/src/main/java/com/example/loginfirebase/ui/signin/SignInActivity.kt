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

        setup()
    }

    private fun setup() {

        val db = Firebase.firestore

        binding.buttonRegister.setOnClickListener {
            val name = binding.editTextNomUsuari.text
            val email = binding.editTextCorreuUsuari.text
            val password = binding.editTextContrasenyaUsuari.text

            val createdUser = User(name.toString(), email.toString(), password.toString())

            if (email.isNotEmpty() && password.isNotEmpty()) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.toString(), password.toString())
                    .addOnCompleteListener { register ->
                        if (register.isSuccessful) {

                            register.result.user?.let { user ->
                                db.collection(getString(R.string.users_collection)).document(user.uid)
                                    .set(createdUser)
                                    .addOnSuccessListener {
                                        Toast.makeText(applicationContext,"S'ha creat el document",Toast.LENGTH_SHORT).show()
                                    }
                                    .addOnFailureListener {
                                        Toast.makeText(applicationContext,"No s'ha pogut crear el document",Toast.LENGTH_SHORT).show()
                                    }

                                goToLogin(register.result?.user?.email ?: "", password.toString())
                            }
                        } else {
                            showAlert("Ha ocurrido un error al crear la cuenta")
                        }
                    }
            }
        }

        binding.buttonToLogin.setOnClickListener(){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showAlert(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage(message)
        builder.setPositiveButton("Acceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun goToLogin(email: String, password: String) {
        val intentLogin = Intent(this, LoginActivity::class.java).apply {
            putExtra("email", email)
            putExtra("password", password)
        }
        startActivity(intentLogin)
    }
}