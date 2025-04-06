package com.cricbuzz.weatherapp.screens

import com.google.gson.annotations.SerializedName

data class WeatherForecastResponse(
    @SerializedName("list")
    var list: List<ForecastItem>? = null,

    @SerializedName("city")
    var city: City? = null,

    @SerializedName("message")
    var message: String? = null
)

data class ForecastItem(
    @SerializedName("dt")
    var dt: Long? = null,

    @SerializedName("main")
    var main: MainWeatherData? = null,

    @SerializedName("weather")
    var weather: List<WeatherDescription>? = null,

    @SerializedName("dt_txt")
    var dtTxt: String? = null
)

data class MainWeatherData(
    @SerializedName("temp")
    var temp: Double? = null,

    @SerializedName("temp_min")
    var tempMin: Double? = null,

    @SerializedName("temp_max")
    var tempMax: Double? = null,

    @SerializedName("humidity")
    var humidity: Int? = null
)

data class WeatherDescription(
    @SerializedName("main")
    var main: String? = null,

    @SerializedName("description")
    var description: String? = null,

    @SerializedName("icon")
    var icon: String? = null
)

data class City(
    @SerializedName("name")
    var name: String? = null,

    @SerializedName("country")
    var country: String? = null
)
