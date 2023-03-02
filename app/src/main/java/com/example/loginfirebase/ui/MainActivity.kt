package com.example.loginfirebase.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.loginfirebase.R
import com.example.loginfirebase.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = this.findNavController(R.id.myNavHostFragment)

        NavigationUI.setupActionBarWithNavController(this,navController)
        NavigationUI.setupWithNavController(binding.bottomNavigationView,navController)

        val bundle: Bundle? = intent.extras
        val email: String? = bundle?.getString("email")
        val password: String? = bundle?.getString("password")

        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.putString("email", email)
        prefs.putString("password", password)
        prefs.apply()
    }

}