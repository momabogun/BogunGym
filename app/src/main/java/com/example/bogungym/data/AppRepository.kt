package com.example.bogungym.data

import androidx.lifecycle.LiveData
import com.example.bogungym.data.db.ExercisesDatabase
import com.example.bogungym.data.model.Exercises
import com.example.bogungym.data.model.UserWorkout
import com.example.bogungym.data.remote.ExercisesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AppRepository(private val api: ExercisesApi, private val database: ExercisesDatabase) {

    fun addWorkout(workout: UserWorkout){
        database.exercisesDao.insertWorkout(workout)
    }
    suspend fun deleteWorkout(workout: UserWorkout){
        database.exercisesDao.deleteWorkout(workout.id)
    }

    val workoutList: LiveData<List<UserWorkout>> = database.exercisesDao.getAllWorkouts()


    fun getExerciseByMuscle(target: String): LiveData<List<Exercises>> = database.exercisesDao.getExerciseByMuscle(target)


    fun getExerciseByID(id: String): LiveData<Exercises> = database.exercisesDao.getExerciseByID(id)


    val exercisesList: LiveData<List<Exercises>> = database.exercisesDao.getAllExercises()

    suspend fun getExercises(){
        withContext(Dispatchers.IO){
            val newCharList = api.retrofitService.getExercises()
            database.exercisesDao.insertAll(newCharList)
        }
    }



}