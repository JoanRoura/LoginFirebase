package com.example.loginfirebase.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.loginfirebase.model.User

class UserViewModel {

    private val repository : UserRepositori
    private val _allUsers = MutableLiveData<List<User>>()
    val allUsers : LiveData<List<User>> = _allUsers


    init {

        repository = UserRepositori().getInstance()
        repository.loadUsers(_allUsers)
    }
}