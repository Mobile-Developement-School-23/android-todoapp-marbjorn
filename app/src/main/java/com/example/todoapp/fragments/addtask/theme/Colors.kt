package com.example.todoapp.fragments.addtask.theme

import android.graphics.Color.parseColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.ColorModel
import androidx.compose.ui.graphics.colorspace.ColorSpace
import androidx.compose.ui.res.colorResource
import androidx.core.graphics.toColorInt
import com.example.todoapp.R

@Immutable
data class CustomColorsLight (
    val separator : Color = Color(color = parseColor("#33000000")),
    val overlay : Color = Color(color = parseColor("#0F000000")),
    val labelPrimary : Color = Color(color = parseColor("#00000000")),
    val laberSecondary : Color = Color(color = parseColor("#99000000")),
    val labelTertiary : Color = Color(color = parseColor("#4D000000")),
    val labelDisable : Color = Color(color = parseColor("#26000000")),
    val red : Color = Color(color = parseColor("#FF3B30")),
    val green : Color = Color(color = parseColor("#34C759")),
    val blue : Color = Color(color = parseColor("#007AFF")),
    val gray : Color = Color(color = parseColor("#8E8E93")),
    val grayLight : Color = Color(color = parseColor("#D1D1D6")),
    val white : Color = Color(color = parseColor("#FFFFFF")),
    val backPrimary : Color = Color(color = parseColor("#F7F6F2")),
    val backSecondary : Color = Color(color = parseColor("#FFFFFF")),
    val backElevated : Color = Color(color = parseColor("#FFFFFF")),
)

val LocalColorsLight = compositionLocalOf { CustomColorsLight() }

val MaterialTheme.colors : CustomColorsLight
    @Composable
    @ReadOnlyComposable
    get() = LocalColorsLight.current

/*
*
*
*     <color name="support_light_separator">#33000000</color>
    <color name="support_light_overlay">#0F000000</color>
    <color name="label_light_primary">#000000</color>
    <color name="label_light_secondary">#99000000</color>
    <color name="label_light_tertiary">#4D000000</color>
    <color name="label_light_disable">#26000000</color>
    <color name="color_light_red">#FF3B30</color>
    <color name="color_light_green">#34C759</color>
    <color name="color_light_blue">#007AFF</color>
    <color name="color_light_gray">#8E8E93</color>
    <color name="color_light_gray_light">#D1D1D6</color>
    <color name="color_light_white">#FFFFFF</color>
    <color name="back_light_primary">#F7F6F2</color>
    <color name="back_light_secondary">#FFFFFF</color>
    <color name="back_light_elevated">#FFFFFF</color>
    *
    * */

data class CustomColorsDark (
    val separator : Color = Color(color = parseColor("#33000000")),
    val overlay : Color = Color(color = parseColor("#0F000000")),
    val labelPrimary : Color = Color(color = parseColor("#00000000")),
    val laberSecondary : Color = Color(color = parseColor("#99000000")),
    val labelTertiary : Color = Color(color = parseColor("#4D000000")),
    val labelDisable : Color = Color(color = parseColor("#26000000")),
    val red : Color = Color(color = parseColor("#FF3B30")),
    val green : Color = Color(color = parseColor("#34C759")),
    val blue : Color = Color(color = parseColor("#007AFF")),
    val gray : Color = Color(color = parseColor("#8E8E93")),
    val grayLight : Color = Color(color = parseColor("#D1D1D6")),
    val white : Color = Color(color = parseColor("#FFFFFF")),
    val backPrimary : Color = Color(color = parseColor("#F7F6F2")),
    val backSecondary : Color = Color(color = parseColor("#FFFFFF")),
    val backElevated : Color = Color(color = parseColor("#FFFFFF")),
)

val LocalColorsDark = compositionLocalOf { CustomColorsDark() }