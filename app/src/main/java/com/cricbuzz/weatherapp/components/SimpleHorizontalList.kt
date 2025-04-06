package com.cricbuzz.weatherapp.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.cricbuzz.weatherapp.utils.Dimens.paddingMedium
import com.cricbuzz.weatherapp.utils.Dimens.paddingSmall
import com.cricbuzz.weatherapp.utils.Dimens.paddingSmallMedium
import com.cricbuzz.weatherapp.utils.Dimens.size_80

@Composable
fun <T> SimpleHorizontalList(
    modifier: Modifier = Modifier,
    items: List<T>,
    isLoading: Boolean = false,
    itemContent: @Composable (T) -> Unit,
    emptyMessage: String = "No Items"
) {
    Box(modifier = modifier.fillMaxWidth()) {
        if (items.isEmpty() && !isLoading) {
            CustomText(
                text = emptyMessage,
                modifier = Modifier
                    .padding(paddingMedium)
                    .align(Alignment.CenterStart),
            )
        } else {
            LazyRow(
                contentPadding = PaddingValues(horizontal = paddingMedium, vertical = paddingSmall),
                horizontalArrangement = Arrangement.spacedBy(paddingSmallMedium)
            ) {
                items(items) { item ->
                    itemContent(item)
                }
                if (isLoading) {
                    item {
                        Box(
                            modifier = Modifier
                                .width(size_80)
                                .height(size_80),
                            contentAlignment = Alignment.Center
                        ) {
                            ProgressDialog()
                        }
                    }
                }
            }
        }
    }
}
