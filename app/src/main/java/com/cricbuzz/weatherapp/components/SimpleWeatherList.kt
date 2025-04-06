package com.cricbuzz.weatherapp.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.cricbuzz.weatherapp.utils.Dimens.paddingMedium
import com.cricbuzz.weatherapp.utils.Dimens.paddingSmall

@Composable
fun <T> SimpleWeatherList(
    modifier: Modifier = Modifier,
    items: List<T>,
    isLoading: Boolean,
    itemContent: @Composable (T) -> Unit,
    emptyMessage: String = "No Items"
) {
    Box(modifier = modifier.fillMaxSize()) {
        when {
            items.isEmpty() && isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingMedium),
                    contentAlignment = Alignment.Center
                ) {
                    ProgressDialog()
                }
            }
            items.isEmpty() && !isLoading -> {
                CustomText(
                    text = emptyMessage,
                    modifier = Modifier.padding(paddingMedium)
                )
            }

            else -> {
                LazyColumn(
                    contentPadding = PaddingValues(paddingMedium),
                    verticalArrangement = Arrangement.spacedBy(paddingSmall)
                ) {
                    items(items) { item ->
                        itemContent(item)
                    }
                }
            }
        }
    }
}
