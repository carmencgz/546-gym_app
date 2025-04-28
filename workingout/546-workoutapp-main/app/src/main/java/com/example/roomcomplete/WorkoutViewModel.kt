package com.example.roomcomplete

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class WorkoutViewModel(application: Application) : AndroidViewModel(application) {
    private val repo: WorkoutRepository
    val workouts: LiveData<List<Workout>>

    init {
        val dao = WorkoutDatabase.getDatabase(application).workoutDao()
        repo = WorkoutRepository(dao)
        workouts = repo.workouts.asLiveData()
    }

    fun addWorkout(
        date: String,
        text: String,
        activity: String,
        mood: String,
        music: String,
        weights: String,
        machines: String,
        imagePath: String?
    ) = viewModelScope.launch {
        val workout = Workout(
            date = date,
            text = text,
            activity = activity,
            mood = mood,
            music = music,
            weights = weights,
            machines = machines,
            imagePath = imagePath
        )
        repo.addWorkout(workout)
    }

    fun deleteWorkout(workout: Workout) = viewModelScope.launch {
        repo.deleteWorkout(workout)
    }

    suspend fun getAllWorkouts(): List<Workout> {
        return repo.workouts.first()
    }
}
