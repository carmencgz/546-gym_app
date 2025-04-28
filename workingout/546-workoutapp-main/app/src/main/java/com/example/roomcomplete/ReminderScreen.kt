// ReminderScreen.kt
package com.example.roomcomplete

import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.work.*
import java.util.*
import java.util.concurrent.TimeUnit

@Composable
fun ReminderScreen(navController: NavHostController) {
    val context = LocalContext.current
    var isReminderEnabled by remember { mutableStateOf(false) }
    var reminderHour by remember { mutableStateOf(9) }
    var reminderMinute by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Enable Reminder", style = MaterialTheme.typography.bodyLarge)
            Switch(
                checked = isReminderEnabled,
                onCheckedChange = { isChecked ->
                    isReminderEnabled = isChecked
                    if (isChecked) {
                        scheduleDailyReminder(context, reminderHour, reminderMinute)
                    } else {
                        cancelDailyReminder(context)
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val timePicker = TimePickerDialog(
                    context,
                    { _, hour: Int, minute: Int ->
                        reminderHour = hour
                        reminderMinute = minute
                        if (isReminderEnabled) {
                            scheduleDailyReminder(context, reminderHour, reminderMinute)
                        }
                    },
                    reminderHour,
                    reminderMinute,
                    true
                )
                timePicker.show()
            },
            enabled = isReminderEnabled,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Pick Reminder Time")
        }
    }
}

private fun scheduleDailyReminder(context: Context, hour: Int, minute: Int) {
    val now = Calendar.getInstance()
    val reminderTime = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, hour)
        set(Calendar.MINUTE, minute)
        set(Calendar.SECOND, 0)
    }

    if (reminderTime.before(now)) {
        reminderTime.add(Calendar.DAY_OF_MONTH, 1)
    }

    val delay = reminderTime.timeInMillis - now.timeInMillis

    val workRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
        .setInitialDelay(delay, TimeUnit.MILLISECONDS)
        .addTag("daily_reminder")
        .build()

    WorkManager.getInstance(context).enqueueUniqueWork(
        "daily_reminder",
        ExistingWorkPolicy.REPLACE,
        workRequest
    )
}

private fun cancelDailyReminder(context: Context) {
    WorkManager.getInstance(context).cancelUniqueWork("daily_reminder")
}
