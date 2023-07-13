package com.example.todoapp.fragments.addtask

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.fragment.findNavController
import com.example.todoapp.model.Priority
import com.example.todoapp.model.TodoItemData
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.util.Calendar


val calendar = Calendar.getInstance()
val todoItemData = TodoItemData(
    id = "",
    text = "Preview Example",
    changedAt = calendar.timeInMillis,
    createdAt = calendar.timeInMillis,
    importance = Priority.HIGH,
    deadline = calendar.timeInMillis
)

@OptIn(ExperimentalMaterial3Api::class)
@Preview(name = "Day theme", uiMode = UI_MODE_NIGHT_NO)
@Composable
fun DayAppTheme() {
    AppTheme {
        AddTaskScreen()
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(name = "Night theme", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun NightAppTheme() {
    AppTheme {
        AddTaskScreen()
    }
}

@Preview
@Composable
fun DatePickerDialogPreview() {
    val state = rememberMaterialDialogState()
    CustomDatePickerDialog(deadlineValueState = state)
    state.show()
}
