package com.example.loginfirebase.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    private val _emailUser = MutableLiveData<String>()
    val emailUser: LiveData<String>
        get() = _emailUser

    private val _passwordUser = MutableLiveData<String>()
    val passwordUser: LiveData<String>
        get() = _passwordUser




    private fun loginUser() {



    }
}