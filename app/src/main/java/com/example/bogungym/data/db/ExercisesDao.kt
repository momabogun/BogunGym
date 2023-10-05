package com.example.bogungym.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bogungym.data.model.Exercises


@Dao
interface ExercisesDao {





// EXERCISES
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(exercises: List<Exercises>)

    @Query("SELECT * FROM exercise_table")
    fun getAllExercises(): LiveData<List<Exercises>>

    @Query("SELECT * FROM exercise_table WHERE target = :muscle")
    fun getExerciseByMuscle(muscle: String): LiveData<List<Exercises>>

    @Query("SELECT * FROM exercise_table WHERE id = :id")
    fun getExerciseByID(id: String): LiveData<Exercises>

    @Query ("SELECT COUNT(*) FROM exercise_table")
    suspend fun count(): Int

    @Query("UPDATE exercise_table SET userPick = :picked WHERE id = :id")
    fun updateUserPicks(picked: Boolean,id: String)

    @Query("SELECT * FROM exercise_table WHERE userPick = 1")
    fun getUserPicked(): LiveData<List<Exercises>>

    @Query("UPDATE exercise_table SET userPick = 0 WHERE userPick = 1")
    fun updateAllFalse()















}