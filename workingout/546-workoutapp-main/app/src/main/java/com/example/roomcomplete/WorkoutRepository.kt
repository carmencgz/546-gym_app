package com.example.roomcomplete

class WorkoutRepository(private val dao: WorkoutDao) {
    val workouts = dao.getAllWorkouts()

    suspend fun addWorkout(workout: Workout) {
        dao.insert(workout)
    }

    suspend fun deleteWorkout(workout: Workout) {
        dao.delete(workout)
    }
}