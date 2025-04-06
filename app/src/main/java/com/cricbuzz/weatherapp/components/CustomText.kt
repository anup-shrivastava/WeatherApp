package com.cricbuzz.weatherapp.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import com.cricbuzz.weatherapp.ui.theme.AppThemeColors
import com.cricbuzz.weatherapp.ui.theme.AppTypography

@Composable
fun CustomText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = AppThemeColors.TextPrimary,
    fontSize: TextUnit = AppTypography.bodySmall.fontSize,
    textAlign: TextAlign = TextAlign.Center,
    minLines:Int=1,
    maxLines:Int=Int.MAX_VALUE,
    fontWeight: FontWeight=FontWeight.Normal
) {
    Text(
        text = text,
        color = color,
        fontSize = fontSize,
        textAlign = textAlign,
        modifier = modifier,
        minLines = minLines,
        fontWeight=fontWeight,
        maxLines = maxLines
        )
}