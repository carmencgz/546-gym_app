// WorkoutTimerScreen.kt
package com.example.roomcomplete

import android.os.Vibrator
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay

@Composable
fun WorkoutTimerScreen(navController: NavHostController) {
    val context = LocalContext.current
    var totalTime by remember { mutableStateOf(30) } // seconds
    var timeLeft by remember { mutableStateOf(totalTime) }
    var isRunning by remember { mutableStateOf(false) }
    var customInput by remember { mutableStateOf("") }

    LaunchedEffect(isRunning) {
        while (isRunning && timeLeft > 0) {
            delay(1000L)
            timeLeft--
        }
        if (timeLeft == 0 && isRunning) {
            isRunning = false
            vibratePhone(context)

            playNotificationSound(context)   // ➡️ also play a beep
            timeLeft = totalTime              // ➡️ reset timer automatically

        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Time Left: $timeLeft sec",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { setTime(30) { totalTime = it; timeLeft = it } }) { Text("30s") }
            Button(onClick = { setTime(60) { totalTime = it; timeLeft = it } }) { Text("60s") }
            Button(onClick = { setTime(90) { totalTime = it; timeLeft = it } }) { Text("90s") }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = customInput,
            onValueChange = { customInput = it },
            label = { Text("Custom (seconds)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                customInput.toIntOrNull()?.let {
                    totalTime = it
                    timeLeft = it
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text("Set Custom Time")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { isRunning = true }, enabled = !isRunning) { Text("Start") }
            Button(onClick = { isRunning = false }, enabled = isRunning) { Text("Pause") }
            Button(onClick = {
                isRunning = false
                timeLeft = totalTime
            }) { Text("Reset") }
        }
    }
}

private fun setTime(seconds: Int, setTime: (Int) -> Unit) {
    setTime(seconds)
}

private fun vibratePhone(context: Context) {

    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    vibrator.vibrate(android.os.VibrationEffect.createOneShot(500, android.os.VibrationEffect.DEFAULT_AMPLITUDE))

    try {
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            vibrator.vibrate(
                android.os.VibrationEffect.createOneShot(
                    500,
                    android.os.VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(500)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
private fun playNotificationSound(context: Context) {
    try {
        val notification = android.provider.Settings.System.DEFAULT_NOTIFICATION_URI
        val ringtone = android.media.RingtoneManager.getRingtone(context, notification)
        ringtone.play()
    } catch (e: Exception) {
        e.printStackTrace()
    }

}
