package com.example.bogungym.data.model

import androidx.room.DatabaseView
import androidx.room.Entity
import androidx.room.PrimaryKey
@DatabaseView
@Entity(tableName = "user_workout")
data class UserWorkout(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    val name: String,
    val days: Int

)
