package com.cricbuzz.weatherapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import com.cricbuzz.weatherapp.ui.theme.AppThemeColors
import com.cricbuzz.weatherapp.utils.Dimens.paddingMedium
import com.cricbuzz.weatherapp.utils.Dimens.paddingSmall
import com.cricbuzz.weatherapp.utils.Dimens.size_100
import com.cricbuzz.weatherapp.utils.Dimens.size_200
import com.cricbuzz.weatherapp.utils.Dimens.size_56

@Composable
fun ProgressDialog(
    message: String? = null,
    onDismissRequest: () -> Unit = {}
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Box(
            modifier = Modifier.width(if(message.isNullOrEmpty()) size_100 else size_200)
                .background(Color.White, shape = RoundedCornerShape(paddingSmall)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(paddingMedium)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(size_56)
                        .padding(paddingSmall)
                )
                if (!message.isNullOrEmpty()) {
                    CustomText(
                        text = message,
                        modifier = Modifier
                            .padding(top = paddingMedium)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = AppThemeColors.TextPrimary
                    )
                }
            }
        }
    }
}


