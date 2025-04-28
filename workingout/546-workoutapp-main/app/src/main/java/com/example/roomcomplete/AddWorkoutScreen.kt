package com.example.roomcomplete

import android.app.DatePickerDialog
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.*
import java.util.*

@Composable
fun AddWorkoutScreen(viewModel: WorkoutViewModel, navController: NavHostController) {
    val workouts by viewModel.workouts.observeAsState(emptyList())
    val context = LocalContext.current

    var date by remember { mutableStateOf("") }
    var text by remember { mutableStateOf("") }
    var activity by remember { mutableStateOf("") }
    var mood by remember { mutableStateOf("") }
    var music by remember { mutableStateOf("") }
    var weights by remember { mutableStateOf("") }
    var machines by remember { mutableStateOf("") }

    val datePickerDialog = rememberDatePickerDialog { pickedDate ->
        date = pickedDate
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Main content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter) // Align the main content at the top
        ) {
            @Composable
            fun input(label: String, value: String, onValueChange: (String) -> Unit) {
                OutlinedTextField(
                    value = value,
                    onValueChange = onValueChange,
                    label = { Text(label) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                )
            }

            OutlinedButton(
                onClick = { datePickerDialog.show() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(vertical = 12.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.White,
                    contentColor = MaterialTheme.colorScheme.primary
                ),
                shape = RoundedCornerShape(4.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(if (date.isNotEmpty()) "Date: $date" else "Pick a Date")
                }
            }

            input("Note", text) { text = it }
            input("Activity", activity) { activity = it }
            input("Mood", mood) { mood = it }
            input("Music", music) { music = it }
            input("Weights", weights) { weights = it }
            input("Machines", machines) { machines = it }

            Row {
                Button(
                    onClick = {
                        if (date.isNotBlank()) {
                            viewModel.addWorkout(
                                date, text, activity, mood, music, weights, machines
                            )
                            // Clear fields
                            date = ""; text = ""; activity = ""; mood = ""
                            music = ""; weights = ""; machines = ""

                            // Navigate to the workouts screen
                            navController.navigate("workout_screen")
                        } else {
                            Toast.makeText(context, "Please pick a date first!", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 4.dp)
                ) {
                    Text("Add Workout")
                }

                Button(
                    onClick = {
                        CoroutineScope(Dispatchers.IO).launch {
                            exportToCSV(context, viewModel)
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 4.dp)
                ) {
                    Text("Export CSV")
                }
            }

            Spacer(Modifier.height(16.dp))
        }

        // Home button fixed at the bottom
        Button(
            onClick = { navController.navigate("welcome_screen") },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter) // Align at the bottom center
        ) {
            Text("Home")
        }
    }
}

@Composable
fun rememberDatePickerDialog(onDateSelected: (String) -> Unit): DatePickerDialog {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    return DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val pickedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
            onDateSelected(pickedDate)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
}
