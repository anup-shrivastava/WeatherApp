package com.cricbuzz.weatherapp.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object WeatherUtils {
    fun getFormattedTime(dtTxt: String): String {
        return try {
            val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val outputFormatter = DateTimeFormatter.ofPattern("hh a")
            val dateTime = LocalDateTime.parse(dtTxt, inputFormatter)
            outputFormatter.format(dateTime)
        } catch (e: Exception) {
            ""
        }
    }

    fun getFormattedDate(dtTxt: String): String {
        return try {
            val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val outputFormatter = DateTimeFormatter.ofPattern("EEE, dd MMM")
            val dateTime = LocalDateTime.parse(dtTxt, inputFormatter)
            outputFormatter.format(dateTime)
        } catch (e: Exception) {
            ""
        }
    }

    fun getWeatherIconUrl(iconCode: String?): String {
        return "https://openweathermap.org/img/wn/${iconCode ?: "01d"}@2x.png"
    }

}

fun Double.toCelsius(): Int {
    return (this - 273.15).toInt()
}