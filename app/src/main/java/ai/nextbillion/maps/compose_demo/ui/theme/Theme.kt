package ai.nextbillion.maps.compose_demo.ui.theme

import ai.nextbillion.maps.compose_demo.ui.theme.Purple200
import ai.nextbillion.maps.compose_demo.ui.theme.Purple500
import ai.nextbillion.maps.compose_demo.ui.theme.Purple700
import ai.nextbillion.maps.compose_demo.ui.theme.Shapes
import ai.nextbillion.maps.compose_demo.ui.theme.Teal200
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
  primary = Purple200,
  primaryVariant = Purple700,
  secondary = Teal200
)

private val LightColorPalette = lightColors(
  primary = Purple500,
  primaryVariant = Purple700,
  secondary = Teal200
)

@Composable
public fun NBMapComposeTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit
) {
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