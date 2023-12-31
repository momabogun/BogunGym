package com.example.bogungym.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.bogungym.utils.Converters
import com.example.bogungym.data.model.Exercises



@Database(entities = [Exercises::class], version = 1)
@TypeConverters(Converters::class)
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
                "bogunGym_database"
            ).fallbackToDestructiveMigration()
                .build()
        }
    }
    return INSTANCE
}