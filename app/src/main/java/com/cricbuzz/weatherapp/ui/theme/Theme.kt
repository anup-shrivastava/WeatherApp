package com.cricbuzz.weatherapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import com.cricbuzz.weatherapp.ui.theme.AppThemeColors.Background
import com.cricbuzz.weatherapp.ui.theme.AppThemeColors.OnBackground
import com.cricbuzz.weatherapp.ui.theme.AppThemeColors.OnPrimary
import com.cricbuzz.weatherapp.ui.theme.AppThemeColors.OnSecondary
import com.cricbuzz.weatherapp.ui.theme.AppThemeColors.OnSurface
import com.cricbuzz.weatherapp.ui.theme.AppThemeColors.Primary
import com.cricbuzz.weatherapp.ui.theme.AppThemeColors.Secondary
import com.cricbuzz.weatherapp.ui.theme.AppThemeColors.Surface
import com.cricbuzz.weatherapp.ui.theme.AppThemeColors.Tertiary

private val LightColorPalette = lightColorScheme(
    primary = Primary,
    secondary = Secondary,
    tertiary = Tertiary,
    background = Background,
    surface = Surface,
    onPrimary = OnPrimary,
    onSecondary = OnSecondary,
    onBackground = OnBackground,
    onSurface = OnSurface
)

private val DarkColorPalette = darkColorScheme(
    primary = Primary,
    secondary = Secondary,
    tertiary = Tertiary,
    background = Background,
    surface = Surface,
    onPrimary = OnPrimary,
    onSecondary = OnSecondary,
    onBackground = OnBackground,
    onSurface = OnSurface
)

@Composable
fun WeatherAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }
    MaterialTheme(
        colorScheme = colors,
        typography = AppTypography,
        content = content
    )
}