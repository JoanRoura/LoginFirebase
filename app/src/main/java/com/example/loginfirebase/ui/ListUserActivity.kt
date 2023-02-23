package com.example.loginfirebase.ui

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.loginfirebase.databinding.ActivityListUserBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ListUserActivity : AppCompatActivity() {
    lateinit var binding: ActivityListUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle: Bundle? = intent.extras
        val name: String? = bundle?.getString("name")

        if (name != null) {
            getUsers(name)
        }
    }

    private fun getUsers(name: String) {
        val db = Firebase.firestore

        val listUsers: ArrayList<String> = ArrayList();

        val listViewUsers = binding.listViewUsers

        val docUsers = db.collection("users")

        docUsers.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    listUsers.add(document.id)
                }

                val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listUsers )

                listViewUsers.adapter = arrayAdapter
            }
            .addOnFailureListener { exception ->
                Toast.makeText(applicationContext,"No s'han pogut cargar els usuaris",Toast.LENGTH_SHORT).show()
            }
    }
}