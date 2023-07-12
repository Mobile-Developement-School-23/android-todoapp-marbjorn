package com.example.todoapp.fragments.addtask

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.example.todoapp.fragments.addtask.theme.LocalColorsDark
import com.example.todoapp.fragments.addtask.theme.LocalColorsLight
import com.example.todoapp.fragments.addtask.theme.LocalSpacing
import com.example.todoapp.fragments.addtask.theme.LocalTypography


@Composable
fun AppTheme (darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colorScheme = if (darkTheme) LocalColorsDark else LocalColorsLight
    val colors = colorScheme.current
    val typography = LocalTypography.current
    val spacing = LocalSpacing.current
    MaterialTheme(
        content = content
    )
}
