package com.example.roomcomplete

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.roomcomplete.ui.theme.RoomCompleteTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RoomCompleteTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "welcome_screen") {
                    composable("welcome_screen") {
                        WelcomeScreen(navController)
                    }
                    composable("add_workout_screen") {
                        AddWorkoutScreen(viewModel = viewModel(), navController = navController)
                    }
                    composable("workout_screen") {
                        WorkoutScreen(viewModel = viewModel(), navController = navController)
                    }
                    composable("timer_screen") {
                        WorkoutTimerScreen(navController)
                    }
                    composable("reminder_screen") {
                        ReminderScreen(navController)
                    }
                }
            }
        }
    }
}
