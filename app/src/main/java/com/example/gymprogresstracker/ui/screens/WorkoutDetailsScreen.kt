package com.example.gymprogresstracker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.gymprogresstracker.data.model.Workout
import java.text.DateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutDetailsScreen(
    workout: Workout,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Workout Details") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = workout.exerciseName,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Sets: ${workout.sets}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "Reps: ${workout.reps}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "Weight: ${workout.weight} kg",
                        style = MaterialTheme.typography.bodyLarge
                    )

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        thickness = DividerDefaults.Thickness,
                        color = DividerDefaults.color
                    )

                    Text(
                        text = "Date: ${
                            DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault())
                                .format(workout.date)
                        }",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Keep up the great work 💪!",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
