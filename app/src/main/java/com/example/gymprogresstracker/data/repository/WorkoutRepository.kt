package com.example.gymprogresstracker.data.repository

import com.example.gymprogresstracker.data.dao.WorkoutDao
import com.example.gymprogresstracker.data.model.Workout
import kotlinx.coroutines.flow.Flow

class WorkoutRepository(private val workoutDao: WorkoutDao) {

    val allWorkouts: Flow<List<Workout>> = workoutDao.getAllWorkouts()

    suspend fun insertWorkout(workout: Workout) {
        workoutDao.insertWorkout(workout)
    }

    suspend fun deleteWorkout(workout: Workout) {
        workoutDao.deleteWorkout(workout)
    }

    suspend fun clearAll() {
        workoutDao.clearAll()
    }
}
