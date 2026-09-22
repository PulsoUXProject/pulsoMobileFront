package com.pulso.mobile.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val PulsoLightColorScheme = lightColorScheme(
    primary = PulsoPrimary,
    primaryContainer = PulsoPrimaryLight,
    background = PulsoBackground,
    surface = PulsoSurface,
    onBackground = PulsoTextPrimary,
    onSurface = PulsoTextPrimary,
    onSurfaceVariant = PulsoTextSecondary,
    outline = PulsoBorder,
    error = PulsoOverdueBorder,
)

private val PulsoDarkColorScheme = darkColorScheme(
    primary = PulsoPrimary,
    primaryContainer = PulsoPrimaryLight,
    error = PulsoOverdueBorder,
)

@Composable
fun PulsoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) PulsoDarkColorScheme else PulsoLightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = PulsoTypography,
        content = content,
    )
}
