package com.example.roomcomplete

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workouts")
data class Workout(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String,
    val text: String,
    val activity: String,
    val mood: String,
    val music: String,
    val weights: String,
    val machines: String,
    val imagePath: String? = null
)