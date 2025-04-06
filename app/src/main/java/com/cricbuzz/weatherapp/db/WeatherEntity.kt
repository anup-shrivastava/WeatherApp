package com.cricbuzz.weatherapp.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_data")
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val city: String,
    val json: String,
    val timestamp: Long = System.currentTimeMillis()
)

