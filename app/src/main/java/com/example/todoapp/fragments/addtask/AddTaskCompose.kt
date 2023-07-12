package com.example.todoapp.fragments.addtask

import android.graphics.BlurMaskFilter
import android.graphics.Typeface.NORMAL
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.todoapp.R
import com.example.todoapp.fragments.addtask.theme.colors
import com.example.todoapp.fragments.addtask.theme.customTypography
import com.example.todoapp.fragments.addtask.theme.spacings


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun TodoTextFields() {
    var value_ by rememberSaveable { mutableStateOf("") }
    var expanded by rememberSaveable { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
        Surface(
            shadowElevation = 12.dp,
        ) {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = {/* Do Something*/ }) {
                        Icon(Icons.Filled.ArrowBack, null)
                    }
                },
                actions = {
                    Button(onClick = {},
                       shape = RectangleShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        )) {
                        Text(
                            text = "Сохранить".uppercase(),
                            color = MaterialTheme.colors.blue,
                            style = MaterialTheme.customTypography.buttonText
                            )
                    }
                })
        }
    }) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(
                    top = MaterialTheme.spacings.standartPadding,
                    start = MaterialTheme.spacings.standartPadding,
                    end = MaterialTheme.spacings.standartPadding
                )
        ) {
            OutlinedTextField(
                value = value_,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colors.white,
                    unfocusedContainerColor = MaterialTheme.colors.white,
                    focusedBorderColor = MaterialTheme.colors.grayLight,
                    unfocusedBorderColor = MaterialTheme.colors.grayLight
                ),
                onValueChange = {value_ = it},
                placeholder = {
                    Text("Что надо сделать...",
                        color = MaterialTheme.colors.grayLight)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(
                        minHeight = 100.dp
                    )
                    .offset(y = 3.dp)
                    .shadow(
                        elevation = 2.dp,
                        clip = false,
                    )
                    .offset(y = -3.dp)
            )

            Column(
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 10.dp)
                    .fillMaxWidth()
                    .clickable { expanded = true }
            ) {
                Text(
                    "Приоритет",
                    style = MaterialTheme.customTypography.bodyText,
                    color = MaterialTheme.colors.red
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    Text("Скопировать")
                    Text("Вставить")
                    Text("Настройки")
                }
                Text("Средний", style = MaterialTheme.customTypography.subhead)

            }
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                color = Color.Gray
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(
                    modifier = Modifier.padding(
                        start = 10.dp
                    )
                ) {
                    Text("Дата", style = MaterialTheme.customTypography.bodyText)
                    Text("Не выбрана", style = MaterialTheme.customTypography.subhead)
                }
                Spacer(Modifier.weight(1f))
                Box {
                    Switch(
                        checked = true,
                        onCheckedChange = {}, Modifier.padding(end = 10.dp)
                    )
                }
            }
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                color = Color.Gray
            )

            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = MaterialTheme.colors.red
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_delete_24),
                    contentDescription = null,
                    modifier = Modifier.padding(end = MaterialTheme.spacings.smallPadding)
                )
                Text(text = "Удалить".uppercase(), style = MaterialTheme.customTypography.buttonText)
            }
        }
    }
}
