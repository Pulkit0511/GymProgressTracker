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
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.collectAsState
import com.example.gymprogresstracker.ui.screens.WorkoutDetailsScreen
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.navigation.compose.NavHost
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize database and repository
        val database = DatabaseProvider.getDatabase(applicationContext)
        val repository = WorkoutRepository(database.workoutDao())

        // Create a ViewModel using a factory
        val viewModel by viewModels<WorkoutViewModel> {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    @Suppress("UNCHECKED_CAST") return WorkoutViewModel(repository) as T
                }
            }
        }

        setContent {
            GymProgressTrackerTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController, startDestination = "workoutList",
                    enterTransition = { fadeIn(animationSpec = tween(400)) + slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(400)) },
                    exitTransition = { fadeOut(animationSpec = tween(300)) + slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = tween(300)) },
                    popEnterTransition = { fadeIn(animationSpec = tween(400)) + slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = tween(400)) },
                    popExitTransition = { fadeOut(animationSpec = tween(300)) + slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tween(300)) }
                ) {
                    composable("workoutList") {
                        WorkoutListScreen(
                            viewModel = viewModel, navController = navController
                        )
                    }
                    composable("workoutDetails/{workoutId}") { backStackEntry ->
                        val workoutId = backStackEntry.arguments?.getString("workoutId")
                        val workout =
                            viewModel.workouts.collectAsState().value.find { it.id == workoutId }

                        workout?.let {
                            WorkoutDetailsScreen(
                                workout = it, onBack = { navController.popBackStack() })
                        }
                    }
                }
            }
        }
    }
}
