package com.example.gymprogresstracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "workouts")
data class Workout(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val date: Long = System.currentTimeMillis(),
    val exerciseName: String,
    val sets: Int,
    val reps: Int,
    val weight: Float
)
