package com.example.roomcomplete

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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

import android.app.DatePickerDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import java.time.LocalDate
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
    var imagePath by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(
            onClick = { navController.navigate("workout_screen") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("View Past Workouts")
        }
        @Composable
        fun input(label: String, value: String, onValueChange: (String) -> Unit) {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                label = { Text(label) },
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            )
        }


        val datePickerDialog = rememberDatePickerDialog { pickedDate ->
            date = pickedDate
        }

        Button(
            onClick = { datePickerDialog.show() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(if (date.isNotEmpty()) "Date: $date" else "Pick a Date")
        }
        input("Date", date) { date = it }

        input("Note", text) { text = it }
        input("Activity", activity) { activity = it }
        input("Mood", mood) { mood = it }
        input("Music", music) { music = it }
        input("Weights", weights) { weights = it }
        input("Machines", machines) { machines = it }
        input("Image Path", imagePath) { imagePath = it }

        Row {
            Button(
                onClick = {
                    if (date.isNotBlank()) {
                        viewModel.addWorkout(
                            date, text, activity, mood, music, weights, machines, imagePath
                        )
                        date = ""; text = ""; activity = ""; mood = ""
                        music = ""; weights = ""; machines = ""; imagePath = ""
                    }
                },
                modifier = Modifier.weight(1f).padding(end = 4.dp)
            ) {
                Text("Add Workout")
            }

            Button(
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        exportToCSV(context, viewModel)
                    }
                },
                modifier = Modifier.weight(1f).padding(start = 4.dp)
            ) {
                Text("Export CSV")
            }
        }

        Spacer(Modifier.height(16.dp))

    }
}


@Composable
fun rememberDatePickerDialog(onDateSelected: (String) -> Unit): DatePickerDialog {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    return DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            // ✅ Create date manually as string (year-month-day)
            val pickedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
            onDateSelected(pickedDate)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
}

