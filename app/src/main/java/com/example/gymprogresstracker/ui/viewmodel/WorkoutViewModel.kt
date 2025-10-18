package com.example.gymprogresstracker.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymprogresstracker.data.model.Workout
import com.example.gymprogresstracker.data.repository.WorkoutRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WorkoutViewModel(
    private val repository: WorkoutRepository
) : ViewModel() {

    private val _workouts = MutableStateFlow<List<Workout>>(emptyList())
    val workouts: StateFlow<List<Workout>> = _workouts.asStateFlow()

    init {
        observeWorkouts()
    }

    private fun observeWorkouts() {
        viewModelScope.launch {
            repository.allWorkouts.collect { list ->
                _workouts.value = list
            }
        }
    }

    fun addWorkout(workout: Workout) {
        viewModelScope.launch {
            repository.insertWorkout(workout)
        }
    }

    fun deleteWorkout(workout: Workout) {
        viewModelScope.launch {
            repository.deleteWorkout(workout)
        }
    }

    fun clearAllWorkouts() {
        viewModelScope.launch {
            repository.clearAll()
        }
    }

    fun updateWorkout(workout: Workout) {
        viewModelScope.launch {
            repository.updateWorkout(workout)
        }
    }
}
