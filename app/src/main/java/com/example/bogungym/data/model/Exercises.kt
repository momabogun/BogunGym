package com.example.bogungym.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.bogungym.utils.Converters

@Entity(tableName = "exercise_table")
@TypeConverters(Converters::class)
data class Exercises (
    @PrimaryKey(autoGenerate = false)
    val id: String = "",
    val name: String = "",
    val gifUrl: String = "",
    val equipment: String = "",
    val target: String= "",
    var userPick: Boolean = false,
    val instructions: List<String> = emptyList()

)