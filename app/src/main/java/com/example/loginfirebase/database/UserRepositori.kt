package com.example.loginfirebase.database

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.loginfirebase.model.User
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore

class UserRepositori {

    var db = FirebaseFirestore.getInstance()
    private lateinit var userArrayList: ArrayList<User>

    @Volatile
    private var INSTANCE: UserRepositori? = null

    fun getInstance(): UserRepositori {
        return INSTANCE ?: synchronized(this) {

            val instance = UserRepositori()
            INSTANCE = instance
            instance
        }
    }

    fun loadUsers(userList: MutableLiveData<List<User>>) {
        userArrayList = ArrayList<User>()
        db.collection("users")
            .orderBy("name", com.google.firebase.firestore.Query.Direction.ASCENDING)
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    Log.w(ContentValues.TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }
                for (dc: DocumentChange in snapshots!!.documentChanges) {

                    when (dc.type) {
                        //DocumentChange.Type.ADDED -> Log.d(TAG, "New city: ${dc.document.data}")

                        DocumentChange.Type.ADDED -> userArrayList.add(dc.document.toObject(User::class.java))
                        DocumentChange.Type.MODIFIED -> userArrayList.add(dc.document.toObject(User::class.java))
                        DocumentChange.Type.REMOVED -> userArrayList.add(dc.document.toObject(User::class.java))
                    }
                }
                userList.postValue(userArrayList)
            }
    }
}