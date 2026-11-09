package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val AetherDarkColorScheme = darkColorScheme(
  primary = CyanNeon,
  onPrimary = SlateDark950,
  primaryContainer = SlateDark800,
  onPrimaryContainer = CyanNeon,
  secondary = VioletNeon,
  onSecondary = Color.White,
  secondaryContainer = SlateDark800,
  onSecondaryContainer = VioletNeon,
  tertiary = EmeraldTech,
  onTertiary = Color.White,
  background = SlateDark950,
  onBackground = TextPrimary,
  surface = SlateDark900,
  onSurface = TextPrimary,
  surfaceVariant = SlateDark850,
  onSurfaceVariant = TextSecondary,
  outline = SlateBorder,
  error = RoseTech,
  onError = Color.White
)

@Composable
fun MyApplicationTheme(
  content: @Composable () -> Unit
) {
  MaterialTheme(
    colorScheme = AetherDarkColorScheme,
    typography = Typography,
    content = content
  )
}
