package com.example.loginfirebase.ui

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.loginfirebase.R
import com.example.loginfirebase.databinding.ActivityVerificationBinding
import com.example.loginfirebase.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

enum class ProviderType {
    BASIC,
    GOOGLE
}

class VerificationActivity : AppCompatActivity() {
    lateinit var binding: ActivityVerificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtenim les dades del email i el password del usuari, del activity Login
        val bundle: Bundle? = intent.extras
        val name: String? = bundle?.getString("name")
        val email: String? = bundle?.getString("email")
        val password: String? = bundle?.getString("password")
        val provider: String? = bundle?.getString("provider")
        setup(name ?: "",email ?: "", password ?: "", provider ?: "");

        // Guarda les dades de manera persistent, per que pugui incia sessio de manera automatica a no se que faci sign out
        val prefs =
            getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.putString("email", email)
        prefs.putString("provider", provider)
        prefs.apply()

        binding.buttonAddUser.setOnClickListener {
            addUser()
        }

        var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data: Intent? = result.data

            if (result.resultCode == RESULT_OK) {
                val updatedEmail = data?.getStringExtra("email");
                val updatedPassword = data?.getStringExtra("password");

                binding.textViewEmail.setText(updatedEmail)
                binding.textViewPassword.setText(updatedPassword)

            } else if (result.resultCode == RESULT_CANCELED) {

            }
        }

        binding.buttonGoToEditUser.setOnClickListener {
            val intentEditUser = Intent(this, EditUserActivity::class.java).apply {
                putExtra("name", name)
                putExtra("email", email)
                putExtra("password", password)
            }
            resultLauncher.launch(intentEditUser)
        }

        binding.buttonGoToListUser.setOnClickListener {
            val intentListUser = Intent(this, ListUserActivity::class.java).apply {
                putExtra("name", name)
            }
            startActivity(intentListUser)
        }

        binding.buttonDeleteUser.setOnClickListener {
            if (name != null) {
                deleteUser(name)
            }
        }
    }

    private fun setup(name: String, email: String, password: String, provider: String) {
        title = "Home Page"

        binding.textViewUsername.text = name
        binding.textViewEmail.text = email
        binding.textViewPassword.text = password
        binding.textViewProvider.text = provider

        binding.buttonSignOut.setOnClickListener {
            // Borrar les dades del usuari quan tenca sessio
            val prefs =
                getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
            prefs.clear()
            prefs.apply()

            // Tanca la sessio
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }
    }

    private fun addUser() {
        val db = Firebase.firestore

        // Create a new user with a first and last name
        val user = hashMapOf(
            "first" to "Ada",
            "last" to "Lovelace",
            "born" to 1815
        )

        val userDataClass = User("rhino", "123456", "joan@gmail.com")

        // Add a new document with a generated ID
        db.collection("users")
            .add(userDataClass)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    private fun deleteUser(name: String) {
        val db = Firebase.firestore

        val docUsers = db.collection("users")

        docUsers.document(name)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(applicationContext,"S'ha borrat el usuari correctament", Toast.LENGTH_SHORT).show()
                goToLogin()
            }
            .addOnFailureListener {
                Toast.makeText(applicationContext,"No s'ha pogut borrar el usuari.", Toast.LENGTH_SHORT).show()
            }
    }

    private fun goToLogin() {
        val intentLoginActivity = Intent(this, LoginActivity::class.java)
        startActivity(intentLoginActivity)
    }
}