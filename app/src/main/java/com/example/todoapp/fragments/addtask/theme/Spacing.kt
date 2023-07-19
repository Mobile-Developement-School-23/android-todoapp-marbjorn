package com.example.todoapp.fragments.addtask.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Spacing(
    val smallPadding : Dp = 8.dp,
    val standartPadding : Dp = 16.dp,
    val largePadding : Dp = 24.dp
)

val LocalSpacing = compositionLocalOf { Spacing() }

val MaterialTheme.spacings : Spacing
    @Composable
    @ReadOnlyComposable
    get() = LocalSpacing.current