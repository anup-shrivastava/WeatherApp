package com.cricbuzz.weatherapp.screens

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {
    @GET("/data/2.5/forecast")
    fun getWeatherData(
        @Query("q") city: String,
        @Query("APPID") apiKey: String
    ): Call<WeatherForecastResponse>
}