package com.example.bogungym.data

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.bogungym.data.db.ExercisesDatabase
import com.example.bogungym.data.model.Exercises
import com.example.bogungym.data.model.UserWorkout
import com.example.bogungym.data.model.Workout
import com.example.bogungym.data.remote.ExercisesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

const val TAG = "AppRepositoryTAG"

class AppRepository(private val api: ExercisesApi, private val database: ExercisesDatabase) {

// EXERCISES

    fun getExerciseByMuscle(target: String): LiveData<List<Exercises>> =
        database.exercisesDao.getExerciseByMuscle(target)


    fun getExerciseByID(id: String): LiveData<Exercises> = database.exercisesDao.getExerciseByID(id)

    fun updateAllFalse(){
        database.exercisesDao.updateAllFalse()
    }


    val exercisesList: LiveData<List<Exercises>> = database.exercisesDao.getAllExercises()


    val pickedList : LiveData<List<Exercises>> = database.exercisesDao.getUserPicked()

    suspend fun getExercises() {

        try {
            val exercises = api.retrofitService.getExercises()
            if (database.exercisesDao.count() == 0) {
                database.exercisesDao.insertAll(exercises)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error loading data from API: $e")

        }
    }


    fun updatePick(liked: Boolean, id: String) {
        try {
            database.exercisesDao.updateUserPicks(liked, id)
        } catch (e: Exception) {
            Log.e(TAG, "Error update : $e")
        }
    }










}