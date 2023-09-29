package com.example.bogungym.data.model

import androidx.room.DatabaseView
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Embedded
@Entity(tableName = "exercise_table")
data class Exercises (
    @PrimaryKey(autoGenerate = false)
    val id: String = "",
    val name: String = "",
    val gifUrl: String = "",
    val equipment: String = "",
    val target: String= "",
    var userPick: Boolean = false

)