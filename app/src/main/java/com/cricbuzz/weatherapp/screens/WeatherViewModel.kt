package com.cricbuzz.weatherapp.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cricbuzz.weatherapp.R
import com.cricbuzz.weatherapp.db.WeatherEntity
import com.cricbuzz.weatherapp.utils.NetworkObserver
import com.cricbuzz.weatherapp.utils.ResourceProvider
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val networkObserver: NetworkObserver,
    private val resourceProvider: ResourceProvider
): ViewModel() {
    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _message = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _message

    private val _weatherDetails = MutableStateFlow<WeatherForecastResponse?>(null)
    val weatherDetails: StateFlow<WeatherForecastResponse?> = _weatherDetails

    private val _isNetworkAvailable = MutableStateFlow(false)

    private val _searchCity = MutableStateFlow<String?>("")
    val searchCity: StateFlow<String?> = _searchCity

    private val _isDataAvailable = MutableStateFlow(false)
    val isDataAvailable: StateFlow<Boolean> = _isDataAvailable

    init {
        viewModelScope.launch {
            networkObserver.networkStatus.collect { isConnected ->
                _isNetworkAvailable.value = isConnected
            }
        }
    }

    fun setQuery(value: String) {
        _searchCity.value = value
    }

    fun clearError() {
        _message.value = null
    }

    fun getWeatherData() {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            _message.value = null
            try {
                if (_isNetworkAvailable.value) {
                    repository.getWeatherData(_searchCity.value.toString()).onSuccess {
                        _isDataAvailable.value = true
                        repository.insertWeatherData(WeatherEntity(city =_searchCity.value.toString(), json = Gson().toJson(it))).let {
                            _message.value = if (it) resourceProvider.getString(R.string.data_inserted_successfully) else resourceProvider.getString(
                                R.string.data_not_inserted
                            )
                        }
                        _weatherDetails.value = it
                    }.onFailure {
                        _isDataAvailable.value = false
                        _message.value = it.message
                    }
                } else {
                    repository.getWeatherDataFromDb(_searchCity.value.toString()).onSuccess {
                        _isDataAvailable.value = true
                        _weatherDetails.value = it
                    }.onFailure {
                        _isDataAvailable.value = false
                        _message.value = it.message
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _loading.value = false
            }
        }
    }
}



fun List<ForecastItem>.getThreeDayAverages(): List<ForecastItem> {
    return this
        .filter { it.dt != null && it.main?.temp != null }
        .groupBy { item ->
            val date = Date(item.dt!! * 1000)
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date)
        }
        .mapNotNull { (_, itemsForDay) ->
            val avgTemp = itemsForDay.mapNotNull { it.main?.temp }.averageOrNull() ?: return@mapNotNull null
            itemsForDay.first().copy(
                main = itemsForDay.first().main?.copy(temp = avgTemp)
            )
        }
        .take(4)
}

fun List<Double>.averageOrNull(): Double? {
    return if (this.isEmpty()) null else this.average()
}

