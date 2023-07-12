package com.example.todoapp.fragments.addtask.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val _largeTitle = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight.Medium,
    fontSize = 32.sp,
    lineHeight = 39.sp
)

val _title = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight.Medium,
    fontSize = 20.sp,
    lineHeight = 32.sp
)

val _body = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp,
    lineHeight = 24.sp
)

val _buttonText = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp,
    lineHeight = 20.sp
)

val _subhead = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp,
    lineHeight = 20.sp
)

@Immutable
data class CustomTypography(
    val bodyText : TextStyle = _body,
    val buttonText : TextStyle = _buttonText,
    val largeTitle : TextStyle = _largeTitle,
    val title : TextStyle = _title,
    val subhead : TextStyle = _subhead
)

val LocalTypography = compositionLocalOf { CustomTypography() }

val MaterialTheme.customTypography : CustomTypography
    @Composable
    @ReadOnlyComposable
    get() = LocalTypography.current
