package com.example.bogungym.data.model

import androidx.room.DatabaseView
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.DocumentReference

@Entity(tableName = "workout_table")
data class UserWorkout(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var name: String
)
