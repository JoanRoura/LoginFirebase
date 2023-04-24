package com.example.loginfirebase.model

data class CustomWorkout (
    var creator_user: String,
    var name: String,
    var exercises: MutableList<String> = mutableListOf(),
    var creation_date: String
    )
