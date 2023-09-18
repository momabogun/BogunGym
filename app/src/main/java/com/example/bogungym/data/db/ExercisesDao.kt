package com.example.bogungym.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bogungym.data.model.Exercises
import com.example.bogungym.data.model.UserWorkout

@Dao
interface ExercisesDao {



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(exercises: List<Exercises>)

    @Query("SELECT * FROM exercises")
    fun getAllExercises(): LiveData<List<Exercises>>

    @Query("SELECT * FROM exercises WHERE target = :muscle")
    fun getExerciseByMuscle(muscle: String): LiveData<List<Exercises>>

    @Query("SELECT * FROM exercises WHERE id = :id")
    fun getExerciseByID(id: String): LiveData<Exercises>


    @Query("DELETE FROM user_workout WHERE id = :id")
    suspend fun deleteWorkout(id: Long)



    @Query("SELECT * FROM user_workout")
    fun getAllWorkouts(): LiveData<List<UserWorkout>>


    @Insert
    fun insertWorkout(workout: UserWorkout)






}