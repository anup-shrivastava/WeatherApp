package com.cricbuzz.weatherapp.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.cricbuzz.weatherapp.components.CustomText
import com.cricbuzz.weatherapp.components.SimpleHorizontalList
import com.cricbuzz.weatherapp.components.SimpleWeatherList
import com.cricbuzz.weatherapp.ui.theme.AppThemeColors
import com.cricbuzz.weatherapp.utils.Dimens.paddingMedium
import com.cricbuzz.weatherapp.utils.Dimens.paddingSmall
import com.cricbuzz.weatherapp.utils.Dimens.paddingTooSmall
import com.cricbuzz.weatherapp.utils.WeatherUtils.getFormattedDate
import com.cricbuzz.weatherapp.utils.WeatherUtils.getFormattedTime
import com.cricbuzz.weatherapp.utils.WeatherUtils.getWeatherIconUrl
import com.cricbuzz.weatherapp.utils.toCelsius

@Composable
fun WeatherScreen(modifier: Modifier, viewModel: WeatherViewModel = hiltViewModel()) {
    val isLoading by viewModel.loading.collectAsState()
    val weatherState by viewModel.weatherDetails.collectAsState()
    var query = viewModel.searchCity.collectAsState()
    val message by viewModel.error.collectAsState()
    val showUi by viewModel.isDataAvailable.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(message) {
        message?.takeIf { it.isNotBlank() }?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.clearError()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppThemeColors.Gray)
            .padding(paddingMedium)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = paddingMedium),
            horizontalArrangement = Arrangement.spacedBy(paddingSmall),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = query.value?:"",
                onValueChange = {viewModel.setQuery(it)},
                placeholder = { Text("Enter city name") },
                modifier = Modifier.weight(1f),
                singleLine = true,
            )

            Button(
                onClick = {viewModel.getWeatherData()},
                shape = MaterialTheme.shapes.medium
            ) {
                CustomText("Search")
            }
        }

        Spacer(modifier = Modifier.height(paddingSmall))
        if (showUi) {
            CurrentWeatherHeader(
                cityName = (weatherState?.city?.name ?: query.value)?.toString()?:"Unknown City",
                temperature = weatherState?.list?.firstOrNull()?.main?.temp?.toCelsius() ?: 0,
                weatherDescription = weatherState?.list?.firstOrNull()?.weather?.firstOrNull()?.description?.capitalize() ?: "Clear"
            )

            SimpleHorizontalList(
                modifier = modifier,
                items = weatherState?.list?:emptyList(),
                isLoading= isLoading,
                itemContent = { hourlyList ->
                    HourlyForecastItem(hourlyList)
                },
                emptyMessage = "No Forecast Found"
            )

            Spacer(modifier = Modifier.height(paddingTooSmall))

            SimpleWeatherList(
                modifier = modifier,
                items = weatherState?.list?.getThreeDayAverages()?:emptyList(),
                isLoading= isLoading,
                itemContent = { dailyList ->
                    DailyForecastItem(dailyList)
                },
                emptyMessage = "No Forecast Found"
            )
        } else {
            CustomText("Search by city to get weather data")
        }
    }
}

@Composable
fun CurrentWeatherHeader(
    cityName: String,
    temperature: Int,
    weatherDescription: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = cityName,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = "${temperature}°C",
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = weatherDescription,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}


@Composable
fun HourlyForecastItem(forecastItem: ForecastItem?) {
    Column(
        modifier = Modifier
            .width(80.dp)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = getFormattedTime(forecastItem?.dtTxt.toString()),
            style = MaterialTheme.typography.bodySmall
        )

        AsyncImage(
            model = getWeatherIconUrl(forecastItem?.weather?.firstOrNull()?.icon),
            contentDescription = null,
            modifier = Modifier.size(48.dp)
        )

        Text(
            text = "${forecastItem?.main?.temp?.toCelsius()}°C",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun DailyForecastItem(forecastItem: ForecastItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = getFormattedDate(forecastItem.dtTxt.toString()),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = forecastItem.weather?.firstOrNull()?.description.orEmpty(),
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = getWeatherIconUrl(forecastItem.weather?.firstOrNull()?.icon),
                    contentDescription = null,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${forecastItem.main?.temp?.toCelsius()}°C",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}


