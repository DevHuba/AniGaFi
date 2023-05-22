package eu.devhuba.anigafi.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

@SuppressLint("ConflictingOnColor")
private val DarkColorPalette = darkColors(
    primary = LightBlack,
    primaryVariant = Green,
    secondary = ContrastOrange,
    secondaryVariant = Orange,
    background = LightBlack,
    onPrimary = Beige,
    error = Orange2,
    onBackground = LightGreen,
    onSurface = Gray

)

@SuppressLint("ConflictingOnColor")
private val LightColorPalette = lightColors(
    primary = LightBlack,
    primaryVariant = Green,
    secondary = ContrastOrange,
    secondaryVariant = Orange,
    background = LightBlack,
    onPrimary = Beige,
    error = Orange2,
    onBackground = LightGreen,
    onSurface = Gray

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun AniGaFiTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}