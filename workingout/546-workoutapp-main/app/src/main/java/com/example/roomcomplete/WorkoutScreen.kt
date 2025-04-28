package com.example.roomcomplete

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.*
import java.io.File
import java.io.FileWriter

@Composable
fun WorkoutScreen(viewModel: WorkoutViewModel, navController: NavHostController) {
    val workouts by viewModel.workouts.observeAsState(emptyList())
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(
            onClick = { navController.navigate("add_workout_screen") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Workout")
        }

        Spacer(Modifier.height(16.dp))

        LazyColumn {
            items(workouts) { workout ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text("Date: ${workout.date}")
                        Text("Note: ${workout.text}")
                        Text("Activity: ${workout.activity}")
                        Text("Mood: ${workout.mood}")
                        Text("Music: ${workout.music}")
                        Text("Weights: ${workout.weights}")
                        Text("Machines: ${workout.machines}")
                        workout.imagePath?.let { Text("Image Path: $it") }

                        Spacer(modifier = Modifier.height(8.dp)) // small space before buttons

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Button(
                                onClick = {
                                    viewModel.deleteWorkout(workout)
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                            ) {
                                Text("Delete")
                            }
                        }
                    }
                }
            }
        }

    }
}

suspend fun exportToCSV(context: Context, viewModel: WorkoutViewModel) {
    val workouts = viewModel.getAllWorkouts()
    val file = File(context.filesDir, "gym_data.csv")
    val writer = FileWriter(file)

    writer.append("Date,Note,Activity,Mood,Music,Weights,Machines,ImagePath\n")
    for (w in workouts) {
        writer.append(
            "\"${w.date}\",\"${w.text}\",\"${w.activity}\",\"${w.mood}\",\"${w.music}\",\"${w.weights}\",\"${w.machines}\",\"${w.imagePath ?: ""}\"\n"
        )
    }

    writer.flush()
    writer.close()

    withContext(Dispatchers.Main) {
        Toast.makeText(context, "CSV Exported: ${file.absolutePath}", Toast.LENGTH_LONG).show()
    }
}
