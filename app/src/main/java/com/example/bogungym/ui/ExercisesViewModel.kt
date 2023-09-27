package com.example.bogungym.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bogungym.data.AppRepository
import com.example.bogungym.data.db.getDatabase
import com.example.bogungym.data.model.Exercises
import com.example.bogungym.data.model.UserWorkout
import com.example.bogungym.data.remote.ExercisesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExercisesViewModel(application: Application) : AndroidViewModel(application) {


    private val database = getDatabase(application)
    private val repository = AppRepository(ExercisesApi, database)


    val exercises = repository.exercisesList



    fun getExercises(target: String): LiveData<List<Exercises>> = repository.getExerciseByMuscle(target)


    fun getExerciseByID(id: String): LiveData<Exercises> = repository.getExerciseByID(id)





    init {
        loadExercises()
    }



    fun loadExercises() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getExercises()
        }
    }


    fun updatePick(liked: Int, id: String) {
       viewModelScope.launch(Dispatchers.IO) {
            repository.updatePick(liked, id)
       }
   }




}




