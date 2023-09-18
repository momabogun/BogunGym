package com.example.bogungym.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Exercises (
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String,
    val gifUrl: String,
    val equipment: String,
    val target: String
)