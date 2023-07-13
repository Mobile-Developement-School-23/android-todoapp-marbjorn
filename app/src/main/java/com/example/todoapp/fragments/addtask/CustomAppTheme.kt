package com.example.todoapp.fragments.addtask

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.core.content.res.ResourcesCompat
import com.example.todoapp.R
import com.example.todoapp.fragments.addtask.theme.LocalSpacing
import com.example.todoapp.fragments.addtask.theme.body
import com.example.todoapp.fragments.addtask.theme.buttonText
import com.example.todoapp.fragments.addtask.theme.largeTitle
import com.example.todoapp.fragments.addtask.theme.subhead
import com.example.todoapp.fragments.addtask.theme.title
import com.google.accompanist.themeadapter.material.MdcTheme


@OptIn(ExperimentalMaterial3Api::class)
@Preview(uiMode = UI_MODE_NIGHT_YES)
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
        surface = colorResource(id = R.color.back_secondary),
        background = colorResource(id = R.color.back_primary),
        onBackground = colorResource(id = R.color.label_secondary),
        surfaceTint = colorResource(id = R.color.label_primary),
        inverseSurface = colorResource(id = R.color.label_primary)
    )

    MaterialTheme(
        colorScheme = colorScheme,
        content = content,
        typography = typography
    )
}