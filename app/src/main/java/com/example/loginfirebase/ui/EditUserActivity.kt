package com.example.loginfirebase.ui

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.loginfirebase.R
import com.example.loginfirebase.databinding.ActivityEditUserBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EditUserActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = Firebase.firestore

        val bundle: Bundle? = intent.extras
        val name: String? = bundle?.getString("name")
        val email: String? = bundle?.getString("email")
        val password: String? = bundle?.getString("password")

        binding.editTextNameUser.setText(name)
        binding.editTextEmailUser.setText(email)
        binding.editTextPasswordUser.setText(password)

        if (email != null && password != null) {
            binding.buttonEditUser.setOnClickListener {
                val currentEmail = binding.editTextEmailUser.text
                val currentPassword = binding.editTextPasswordUser.text

//                val updatedUser = User("", currentPassword.toString(), currentEmail.toString())

                val currentUser = db.collection(getString(R.string.users_collection)).document(name.toString())

                currentUser
                    .update("email", currentEmail.toString(), "password", currentPassword.toString())
                    .addOnSuccessListener {
                        Toast.makeText(applicationContext,"S'ha actualitzat correctament el usuari.", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(applicationContext,"No s'ha pogut actualitza el usuari.", Toast.LENGTH_SHORT).show()
                    }

                showVerification(currentEmail.toString(), currentPassword.toString());


            }
        }

        binding.buttonGoToVerification.setOnClickListener {
            onBackPressed()
        }
    }

    private fun showVerification(currentEmail: String, currentPassword: String) {
        intent.putExtra("email", currentEmail)
        intent.putExtra("password", currentPassword)
        setResult(Activity.RESULT_OK, intent);
        finish()
    }


}