package com.example.bogungym.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.bogungym.data.model.Exercises
import com.example.bogungym.data.model.UserWorkout

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


// USER WORKOUTS


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllWorkouts(exercises: List<UserWorkout>)
    @Query("SELECT * FROM workout_table")
    fun getAllWorkouts(): LiveData<List<UserWorkout>>


    @Insert
    fun insertWorkout(workout: UserWorkout)

    @Query("SELECT * FROM workout_table WHERE id =:id")
    fun getWorkoutById(id: Long) : LiveData<UserWorkout>



    @Query("DELETE FROM workout_table WHERE id = :id")
    suspend fun delete(id: Long)


    @Query("SELECT COUNT(*) FROM workout_table")
    fun getCount(): Int








}