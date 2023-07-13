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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColorInt
import com.example.todoapp.R
/*
@Immutable
data class CustomColorsLight (
    val separator : Color = Color(color = ContextCompat.getColor(LocalContext.current, R.color.support_separator)),
    val overlay : Color = Color(color = parseColor("#0F000000")),
    val labelDisable : Color = Color(color = parseColor("#26000000")),
    val red : Color = Color(color = parseColor("#FF3B30")),
    val green : Color = Color(color = parseColor("#34C759")),
    val blue : Color = Color(color = parseColor("#007AFF")),
    val gray : Color = Color(color = parseColor("#8E8E93")),
    val grayLight : Color = Color(color = parseColor("#D1D1D6")),
    val white : Color = Color(color = parseColor("#FFFFFF")),
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
    <color name="support_dark_separator">#33FFFFFF</color>
    <color name="support_dark_overlay">#52000000</color>
    <color name="label_dark_primary">#FFFFFF</color>
    <color name="label_dark_secondary">#99FFFFFF</color>
    <color name="label_dark_tertiary">#66FFFFFF</color>
    <color name="label_dark_disable">#26FFFFFF</color>
    <color name="color_dark_red">#FF453A</color>
    <color name="color_dark_green">#32D74B</color>
    <color name="color_dark_blue">#0A84FF</color>
    <color name="color_dark_gray">#8E8E93</color>
    <color name="color_dark_gray_light">#48484A</color>
    <color name="color_dark_white">#FFFFFF</color>
    <color name="back_dark_primary">#161618</color>
    <color name="back_dark_secondary">#252528</color>
    <color name="back_dark_elevated">#3C3C3F</color>
    *
    * */

data class CustomColorsDark (
    val separator : Color = Color(color = parseColor("#33FFFFFF")),
    val overlay : Color = Color(color = parseColor("#52000000")),
    val labelPrimary : Color = Color(color = parseColor("#FFFFFF")),
    val laberSecondary : Color = Color(color = parseColor("#99FFFFFF")),
    val labelTertiary : Color = Color(color = parseColor("#66FFFFFF")),
    val labelDisable : Color = Color(color = parseColor("#26FFFFFF")),
    val red : Color = Color(color = parseColor("#FF453A")),
    val green : Color = Color(color = parseColor("#32D74B")),
    val blue : Color = Color(color = parseColor("#0A84FF")),
    val gray : Color = Color(color = parseColor("#8E8E93")),
    val grayLight : Color = Color(color = parseColor("#48484A")),
    val white : Color = Color(color = parseColor("#FFFFFF")),
    val backPrimary : Color = Color(color = parseColor("#161618")),
    val backSecondary : Color = Color(color = parseColor("#252528")),
    val backElevated : Color = Color(color = parseColor("#3C3C3F")),
)

val LocalColorsDark = compositionLocalOf { CustomColorsDark() }*/