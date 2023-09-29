package com.example.bogungym.data.model

import androidx.room.DatabaseView
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.DocumentReference


data class UserWorkout(
    var name: String = "",
    var exercisesPicked : List<String> = emptyList()
)
