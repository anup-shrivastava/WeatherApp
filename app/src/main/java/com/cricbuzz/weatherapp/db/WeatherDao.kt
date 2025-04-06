package com.cricbuzz.weatherapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(cache: WeatherEntity): Long?

    @Query("SELECT * FROM weather_data WHERE city = :city")
    suspend fun getWeatherForCity(city: String): WeatherEntity?
}
