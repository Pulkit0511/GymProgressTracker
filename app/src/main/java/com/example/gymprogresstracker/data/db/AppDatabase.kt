package com.example.gymprogresstracker.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.gymprogresstracker.data.dao.WorkoutDao
import com.example.gymprogresstracker.data.model.Workout

@Database(entities = [Workout::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun workoutDao(): WorkoutDao
}
