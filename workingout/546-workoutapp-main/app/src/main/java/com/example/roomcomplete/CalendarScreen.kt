// CalendarScreen.kt
package com.example.roomcomplete

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter


@Composable
fun CalendarScreen(viewModel: WorkoutViewModel, navController: NavHostController) {
    val workouts by viewModel.workouts.observeAsState(emptyList())
    val workoutDates = workouts.map { it.date }.toSet()

<<<<<<< HEAD
    val currentMonth = LocalDate.now()
    val previousMonth = currentMonth.minusMonths(1)
=======
    var selectedMonth by remember { mutableStateOf(YearMonth.now()) }

    val daysInMonth = selectedMonth.lengthOfMonth()
    val firstDayOfMonth = selectedMonth.atDay(1).dayOfWeek.value % 7 // Sunday = 0
    val monthFormatter = DateTimeFormatter.ofPattern("yyyy-MM")
    val currentMonthString = selectedMonth.format(monthFormatter)
    val workoutsThisMonth = workoutDates.count { it.startsWith(currentMonthString) }
>>>>>>> 3bc0b95f703692a8d7544294615d31b23a20c35a

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)

    ) {
<<<<<<< HEAD
        LazyColumn(
            modifier = Modifier.weight(1f) // so button stays at the bottom
        ) {
            item {
                CalendarForMonth(previousMonth, workoutDates)
                Spacer(Modifier.height(32.dp))
                CalendarForMonth(currentMonth, workoutDates)
            }
        }

        Button(
            onClick = { navController.navigate("welcome_screen") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Home")
        }
    }
}

@Composable
fun CalendarForMonth(month: LocalDate, workoutDates: Set<String>) {
    val daysInMonth = month.lengthOfMonth()
    val firstDayOfMonth = month.withDayOfMonth(1).dayOfWeek.value % 7 // Sunday = 0

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "${month.month.name.lowercase().replaceFirstChar { it.uppercase() }} ${month.year}",
            style = MaterialTheme.typography.headlineMedium,
=======
        // Month Selector
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = { selectedMonth = selectedMonth.minusMonths(1) }) {
                Text("<")
            }
            Text(
                text = "${selectedMonth.month.name.lowercase().replaceFirstChar { it.uppercase() }} ${selectedMonth.year}",
                style = MaterialTheme.typography.headlineMedium
            )
            Button(onClick = { selectedMonth = selectedMonth.plusMonths(1) }) {
                Text(">")
            }
        }

        Spacer(Modifier.height(16.dp))



        Text(
            text = "You worked out $workoutsThisMonth days this month!",
            style = MaterialTheme.typography.titleMedium,
>>>>>>> 3bc0b95f703692a8d7544294615d31b23a20c35a
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(Modifier.height(16.dp))
<<<<<<< HEAD

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
=======
        // Day Titles (Sun, Mon, etc.)
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
>>>>>>> 3bc0b95f703692a8d7544294615d31b23a20c35a
            listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat").forEach { day ->
                Text(day, style = MaterialTheme.typography.bodyLarge)
            }
        }

        Spacer(Modifier.height(8.dp))

<<<<<<< HEAD
=======
        // Calendar Grid
>>>>>>> 3bc0b95f703692a8d7544294615d31b23a20c35a
        val totalCells = firstDayOfMonth + daysInMonth
        val daysList = (1..totalCells).map { index ->
            if (index <= firstDayOfMonth) "" else (index - firstDayOfMonth).toString()
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp) // control height
        ) {
            items(daysList) { day ->
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(48.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (day.isNotEmpty()) {
<<<<<<< HEAD
                        val fullDate = month.withDayOfMonth(day.toInt())
=======
                        val fullDate = selectedMonth.atDay(day.toInt())
>>>>>>> 3bc0b95f703692a8d7544294615d31b23a20c35a
                        val formatted = fullDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                        val isWorkoutDay = workoutDates.contains(formatted)

                        Text(
                            text = day,
                            style = MaterialTheme.typography.bodyLarge,
                            color = if (isWorkoutDay) Color.White else MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier
                                .background(
                                    if (isWorkoutDay) MaterialTheme.colorScheme.primary else Color.Transparent,
                                    shape = MaterialTheme.shapes.small
                                )
                                .padding(8.dp)
                        )
                    }
                }
            }
        }
    }
}