package com.example.bogungym.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bogungym.data.model.Exercises
import com.example.bogungym.data.model.UserWorkout

@Database(entities = [Exercises::class,UserWorkout::class], version = 1)
abstract class ExercisesDatabase: RoomDatabase() {

    abstract val exercisesDao: ExercisesDao
}


private lateinit var INSTANCE: ExercisesDatabase



fun getDatabase(context: Context): ExercisesDatabase {
    synchronized(ExercisesDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                ExercisesDatabase::class.java,
                "exercises_database"
            ).fallbackToDestructiveMigration()
                .build()
        }
    }
    return INSTANCE
}