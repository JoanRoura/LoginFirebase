package com.example.loginfirebase.model

data class CustomWorkout (
    var exercises: MutableList<String> = mutableListOf(),
    var name: String
    )