package com.example.roomcomplete

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
@Composable
fun WelcomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.welcome_image),
            contentDescription = "App Logo",
            modifier = Modifier.size(200.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text("Welcome to Your Workout Tracker!", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { navController.navigate("add_workout_screen") }, modifier = Modifier.fillMaxWidth()) {
            Text("Add Workout")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate("workout_screen") }, modifier = Modifier.fillMaxWidth()) {
            Text("View Past Workouts")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate("timer_screen") }, modifier = Modifier.fillMaxWidth()) {
            Text("Set Timer")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate("reminder_screen") }, modifier = Modifier.fillMaxWidth()) {
            Text("Set Reminder")
        }
    }
}