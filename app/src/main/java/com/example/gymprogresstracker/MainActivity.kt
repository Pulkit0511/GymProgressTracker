package com.example.gymprogresstracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gymprogresstracker.data.db.DatabaseProvider
import com.example.gymprogresstracker.data.repository.WorkoutRepository
import com.example.gymprogresstracker.ui.screens.WorkoutListScreen
import com.example.gymprogresstracker.ui.theme.GymProgressTrackerTheme
import com.example.gymprogresstracker.ui.viewmodel.WorkoutViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize database and repository
        val database = DatabaseProvider.getDatabase(applicationContext)
        val repository = WorkoutRepository(database.workoutDao())

        // Create a ViewModel using a factory
        val viewModel by viewModels<WorkoutViewModel> {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    @Suppress("UNCHECKED_CAST")
                    return WorkoutViewModel(repository) as T
                }
            }
        }

        setContent {
            GymProgressTrackerTheme {
                WorkoutListScreen(viewModel = viewModel)
            }
        }
    }
}
