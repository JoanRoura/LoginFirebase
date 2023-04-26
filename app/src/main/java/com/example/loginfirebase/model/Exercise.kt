package com.example.loginfirebase.model

import android.widget.EditText

data class Exercise(
    var id : String? = null,
    var name : String? = null,
    var sets : String? = null,
    var reps : String? = null,
    var equipment : String? = null,
    var focus_area : String? = null,
    var image : String? = null,
    var preparation: String? = null,
    var editSets : EditText? = null,
    var editReps : EditText? = null,
    var isExpandable : Boolean = false,
    )
