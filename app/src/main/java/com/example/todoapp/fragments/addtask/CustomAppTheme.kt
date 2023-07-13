package com.example.todoapp.fragments.addtask

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import com.example.todoapp.R
import com.example.todoapp.fragments.addtask.theme.body
import com.example.todoapp.fragments.addtask.theme.buttonText
import com.example.todoapp.fragments.addtask.theme.largeTitle
import com.example.todoapp.fragments.addtask.theme.subhead
import com.example.todoapp.fragments.addtask.theme.title


@Composable
fun AppTheme (darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit = {}) {

    val typography = Typography(
        headlineLarge = largeTitle,
        headlineMedium = title,
        bodyLarge = body,
        labelMedium = buttonText,
        headlineSmall = subhead
    )

    val colorScheme = lightColorScheme(
        primary = colorResource(id = R.color.back_primary),
        onPrimary = colorResource(id = R.color.label_primary),
        primaryContainer = colorResource(id = R.color.back_secondary),
        secondary = colorResource(id = R.color.back_secondary),
        onSecondary = colorResource(id = R.color.label_secondary),
        onTertiary = colorResource(id = R.color.label_tertiary),
        background = colorResource(id = R.color.back_primary)

    )

    MaterialTheme(
        colorScheme = colorScheme,
        content = content,
        typography = typography
    )
}