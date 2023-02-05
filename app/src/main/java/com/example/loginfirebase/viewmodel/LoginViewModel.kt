package com.example.loginfirebase.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class LoginViewModel @Inject constructor() : ViewModel() {

    private val _anarLogin = MutableLiveData<Boolean>()
    val anarLogin: LiveData<Boolean>
        get() = _anarLogin
}