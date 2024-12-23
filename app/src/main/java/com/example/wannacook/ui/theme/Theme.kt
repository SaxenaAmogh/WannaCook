package com.example.wannacook.ui.theme

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.wannacook.R

private val DarkColors = darkColorScheme(
    primary = Yellow80,
    background = Color.Transparent,
    surface = Color.Transparent,
)

private val LightColors = lightColorScheme(
    primary = Yellow80,
    background = Color.Transparent,
    surface = Color.Transparent,
)

@Composable
fun WannaCookTheme(
    useDarkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colors = if (useDarkTheme) {
        DarkColors
    } else {
        LightColors
    }

    // Adjust the status bar icons for edge-to-edge
    val view = LocalView.current
    if (!view.isInEditMode) {
        val activity = LocalContext.current as Activity
        SideEffect {
            // Enable edge-to-edge
            WindowCompat.setDecorFitsSystemWindows(activity.window, false)

            // Adjust status bar icon appearance
            val isAppearanceLightStatusBars = !useDarkTheme
            WindowInsetsControllerCompat(activity.window, view).isAppearanceLightStatusBars = isAppearanceLightStatusBars
        }
    }

    MaterialTheme(
        colorScheme = colors,
        content = {
            // Apply gradient background
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                Surface(color = Color.Transparent) {
                    content()
                }
            }
        }
    )
}
