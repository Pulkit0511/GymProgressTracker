package com.example.gymprogresstracker.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.gymprogresstracker.data.model.Workout
import com.example.gymprogresstracker.ui.viewmodel.WorkoutViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import java.text.DateFormat
import java.util.*
import androidx.compose.ui.draw.shadow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Edit
import androidx.compose.ui.Alignment
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.navigation.NavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutListScreen(viewModel: WorkoutViewModel, navController: NavController) {
    val workouts by viewModel.workouts.collectAsState()
    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by remember { mutableStateOf(false) }
    var workoutToEdit by remember { mutableStateOf<Workout?>(null) }
    var exerciseName by remember { mutableStateOf(TextFieldValue("")) }
    var sets by remember { mutableStateOf(TextFieldValue("")) }
    var reps by remember { mutableStateOf(TextFieldValue("")) }
    var weight by remember { mutableStateOf(TextFieldValue("")) }

    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Gym Progress Tracker 💪") },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        )
    }, floatingActionButton = {
        FloatingActionButton(
            onClick = {
                workoutToEdit = null
                exerciseName = TextFieldValue("")
                sets = TextFieldValue("")
                reps = TextFieldValue("")
                weight = TextFieldValue("")
                isSheetOpen = true
            }, containerColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Workout")
        }
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(paddingValues)
        ) {
            HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
            Spacer(Modifier.height(16.dp))

            // List section
            Text("Workout History", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(workouts) { workout ->
                    WorkoutCard(
                        workout = workout,
                        onDelete = { viewModel.deleteWorkout(workout) },
                        onEdit = {
                            workoutToEdit = workout
                            exerciseName = TextFieldValue(workout.exerciseName)
                            sets = TextFieldValue(workout.sets.toString())
                            reps = TextFieldValue(workout.reps.toString())
                            weight = TextFieldValue(workout.weight.toString())
                            isSheetOpen = true
                        },
                        onClick = { navController.navigate("workoutDetails/${workout.id}") })

                }
            }
        }

        if (isSheetOpen) {
            ModalBottomSheet(
                onDismissRequest = { isSheetOpen = false },
                sheetState = sheetState,
                dragHandle = { BottomSheetDefaults.DragHandle() },
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Add New Workout", style = MaterialTheme.typography.titleLarge)
                    }
                    Spacer(Modifier.height(12.dp))

                    TextField(
                        value = exerciseName,
                        onValueChange = { exerciseName = it },
                        label = { Text("Exercise Name") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(12.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        TextField(
                            value = sets,
                            onValueChange = { sets = it },
                            label = { Text("Sets") },
                            modifier = Modifier.weight(1f)
                        )
                        TextField(
                            value = reps,
                            onValueChange = { reps = it },
                            label = { Text("Reps") },
                            modifier = Modifier.weight(1f)
                        )
                        TextField(
                            value = weight,
                            onValueChange = { weight = it },
                            label = { Text("Weight (kg)") },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(Modifier.height(24.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedButton(onClick = { isSheetOpen = false }) {
                            Text("Cancel")
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Button(
                            onClick = {
                                if (exerciseName.text.isNotBlank()) {
                                    val updatedWorkout = workoutToEdit?.copy(
                                        exerciseName = exerciseName.text,
                                        sets = sets.text.toIntOrNull() ?: 0,
                                        reps = reps.text.toIntOrNull() ?: 0,
                                        weight = weight.text.toFloatOrNull() ?: 0f
                                    ) ?: Workout(
                                        exerciseName = exerciseName.text,
                                        sets = sets.text.toIntOrNull() ?: 0,
                                        reps = reps.text.toIntOrNull() ?: 0,
                                        weight = weight.text.toFloatOrNull() ?: 0f
                                    )

                                    if (workoutToEdit != null) {
                                        viewModel.updateWorkout(updatedWorkout)
                                    } else {
                                        viewModel.addWorkout(updatedWorkout)
                                    }

                                    // Reset fields
                                    exerciseName = TextFieldValue("")
                                    sets = TextFieldValue("")
                                    reps = TextFieldValue("")
                                    weight = TextFieldValue("")
                                    workoutToEdit = null
                                    isSheetOpen = false
                                }
                            }) {
                            Text("Add Workout")
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun WorkoutCard(
    workout: Workout, onDelete: () -> Unit, onEdit: () -> Unit, onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp, RoundedCornerShape(12.dp))
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(Modifier.padding(12.dp)) {
            Text(workout.exerciseName, style = MaterialTheme.typography.titleMedium)
            Text("Sets: ${workout.sets}, Reps: ${workout.reps}, Weight: ${workout.weight} kg")
            Text(
                "Date: ${
                    DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault())
                        .format(workout.date)
                }"
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onEdit) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Workout",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Workout",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}
