package com.cricbuzz.weatherapp.screens

import com.cricbuzz.weatherapp.R
import com.cricbuzz.weatherapp.db.WeatherDatabase
import com.cricbuzz.weatherapp.db.WeatherEntity
import com.cricbuzz.weatherapp.di.ApiURL.API_KEY
import com.cricbuzz.weatherapp.utils.ResourceProvider
import com.google.gson.Gson
import timber.log.Timber
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val apiService: ApiServices,
    private val resourceProvider: ResourceProvider,
    private val db: WeatherDatabase,
) {
    suspend fun getWeatherData(city: String): Result<WeatherForecastResponse> {
        return try {
            val response = apiService.getWeatherData(city, API_KEY).execute()
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Result.success(result)
            } else {
                val errorMessage = try {
                    val errorJson = response.errorBody()?.string()
                    val gson = Gson()
                    val errorResponse = gson.fromJson(errorJson, WeatherForecastResponse::class.java)
                    errorResponse?.message ?: resourceProvider.getString(R.string.failed_to_fetch_weather_data)
                } catch (e: Exception) {
                    resourceProvider.getString(R.string.failed_to_fetch_weather_data)
                }
                Result.failure(Exception(errorMessage))            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(Exception(resourceProvider.getString(R.string.failed_to_fetch_weather_data)))
        }
    }

    suspend fun getWeatherDataFromDb(city: String): Result<WeatherForecastResponse> {
        return try {
            val response = db.weatherDao().getWeatherForCity(city)?.let {
                Timber.tag("Weather Data").d("WeatherData: City: ${it.city}, Json: ${it.json}")
                Gson().fromJson(it.json, WeatherForecastResponse::class.java)
            }
            if (response!=null) {
                Result.success(response)
            } else {
                Result.failure(Exception(resourceProvider.getString(R.string.city_not_found_locally)))
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(Exception(resourceProvider.getString(R.string.failed_to_fetch_weather_data)))
        }
    }

    suspend fun insertWeatherData(data: WeatherEntity):Boolean {
        return try {
            Timber.tag("Weather Data").d("insertWeatherData: $data")
            return db.weatherDao().insertOrReplace(data) != -1L
        } catch (ex: Exception) {
            ex.printStackTrace()
            false
        }
    }
}
