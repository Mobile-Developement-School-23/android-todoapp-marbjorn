package com.example.todoapp.fragments.addtask

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
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
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.todoapp.R
import com.example.todoapp.fragments.addtask.theme.colors
import com.example.todoapp.fragments.addtask.theme.customTypography
import com.example.todoapp.fragments.addtask.theme.spacings
import com.example.todoapp.model.Priority
import com.example.todoapp.model.TodoItemData
import com.example.todoapp.vm.AddTaskModel
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import java.text.SimpleDateFormat
import java.time.ZoneOffset
import java.util.Calendar
import java.util.Locale
import java.util.UUID

@ExperimentalMaterial3Api
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen (
    viewModel: AddTaskModel,
    navController: NavController
) {
    val initialTodoItem = viewModel.stateTodoItem.value

    var dropdownMenuExpanded by rememberSaveable { mutableStateOf(false) }
    var textValue by rememberSaveable { mutableStateOf("") }
    var priorityValue by remember { mutableStateOf(Priority.MEDIUM) }

    var switchValue by remember { mutableStateOf(false) }
    val deadlineValueState = rememberSheetState()
    var deadlineDate by remember {
        mutableStateOf<Long?>(null)
    }

    var deadlineString by remember { mutableStateOf("") }

    var deleteButtonEnabled by remember {
        mutableStateOf(false)
    }

    CalendarDialog(
        state = deadlineValueState,
        selection = CalendarSelection.Date { date ->
            run {
                deadlineDate = date.atStartOfDay().toEpochSecond(ZoneOffset.UTC) * 1000
                deadlineString = stringDate(deadlineDate)
            }
        })


    //initializing add task to change item
    if (viewModel.stateInit.value && viewModel.stateTodoItem.value != null) {
        Log.d("Id", initialTodoItem.toString())
        deleteButtonEnabled = true
        switchValue = viewModel.stateTodoItem.value!!.deadline != null
        textValue = viewModel.stateTodoItem.value!!.text
        priorityValue = viewModel.stateTodoItem.value!!.importance
        deadlineDate = viewModel.stateTodoItem.value!!.deadline
        deadlineString = stringDate(deadlineDate)
    }



    Scaffold(
        topBar = {
            Surface(
                shadowElevation = 12.dp,
            ) {
                TopAppBar(
                    title = {},
                    navigationIcon = {
                        IconButton(onClick = {   //close button
                            navController.navigate(R.id.action_addTaskFragment_to_todoListFragment)
                        }) {
                            Icon(Icons.Outlined.Close, null)
                        }
                    },
                    actions = {
                        Button(
                            onClick = { //save button
                                viewModel.addOrUpdate(
                                    todoItemDB = TodoItemData(
                                        id = UUID.randomUUID().toString(),
                                        text = textValue,
                                        importance = priorityValue,
                                        deadline = deadlineDate,
                                        createdAt = if (initialTodoItem != null)
                                            initialTodoItem.createdAt
                                        else Calendar.getInstance().timeInMillis,
                                        changedAt = Calendar.getInstance().timeInMillis
                                    )
                                )
                                navController.navigate(R.id.action_addTaskFragment_to_todoListFragment)
                            },
                            shape = RectangleShape,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent
                            )
                        ) {
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
            OutlinedTextField( //text field
                value = textValue,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colors.white,
                    unfocusedContainerColor = MaterialTheme.colors.white,
                    focusedBorderColor = MaterialTheme.colors.grayLight,
                    unfocusedBorderColor = MaterialTheme.colors.grayLight
                ),
                onValueChange = { textValue = it },
                placeholder = {
                    Text(
                        "Что надо сделать...",
                        color = MaterialTheme.colors.grayLight
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(
                        minHeight = 100.dp
                    )
                    .offset(y = 3.dp) //shifting shadow
                    .shadow(
                        elevation = 2.dp,
                        clip = false,
                    )
                    .offset(y = -3.dp)
            )

            //section with priority
            Column(
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 10.dp)
                    .fillMaxWidth()
                    .clickable { dropdownMenuExpanded = true }
            ) {
                Text( //Importance label
                    stringResource(id = R.string.importance),
                    style = MaterialTheme.customTypography.title
                )
                DropdownMenu( //Dropdown menu
                    expanded = dropdownMenuExpanded,
                    onDismissRequest = { dropdownMenuExpanded = false },
                    modifier = Modifier.padding(MaterialTheme.spacings.standartPadding)
                ) {
                    Text(stringResource(
                        id = R.string.priority_high
                    ),
                        color = MaterialTheme.colors.red,
                        modifier = Modifier.padding(bottom = MaterialTheme.spacings.smallPadding)
                            .clickable {
                            priorityValue = Priority.HIGH
                            dropdownMenuExpanded = false
                        }
                    )
                    Text(stringResource(
                        id = R.string.priority_common
                    ),
                        modifier = Modifier.padding(bottom = MaterialTheme.spacings.smallPadding)
                            .clickable {
                            priorityValue = Priority.MEDIUM
                            dropdownMenuExpanded = false
                        }
                    )
                    Text(stringResource(
                        id = R.string.priority_low
                    ),
                        modifier = Modifier.clickable {
                            priorityValue = Priority.LOW
                            dropdownMenuExpanded = false
                        }
                    )
                }
                // current priority
                Text(
                    text = stringPriorityResource(priority = priorityValue),
                    style = MaterialTheme.customTypography.subhead
                )

            }

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                color = Color.Gray
            )

            //deadline section
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable {
                if (switchValue) deadlineValueState.show()
            }) {
                Column(
                    modifier = Modifier.padding(
                        start = 10.dp
                    )
                ) {
                    //subsection with date labels
                    Text(
                        stringResource(id = R.string.date),
                        style = MaterialTheme.customTypography.title
                    )
                    Text(deadlineString, style = MaterialTheme.customTypography.subhead)
                }
                //spacing between subsection and switch
                Spacer(Modifier.weight(1f))

                //deadline enable switch
                Switch(
                    checked = switchValue,
                    onCheckedChange = {
                        switchValue = it
                        Log.d("Switch", it.toString())
                        if (switchValue == true) {
                            deadlineValueState.show()
                        } else {
                            deadlineDate = null
                            deadlineString = ""
                        }
                    },

                    modifier = Modifier.padding(end = 10.dp)
                )
            }
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                color = Color.Gray
            )
            //delete button
            Button(
                enabled = deleteButtonEnabled,
                onClick = {
                    if (initialTodoItem != null) {
                        viewModel.delete(todoItemDB = initialTodoItem)
                    }
                    navController.navigate(R.id.action_addTaskFragment_to_todoListFragment)
                },
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
                Text(
                    text = stringResource(id = R.string.delete).uppercase(),
                    style = MaterialTheme.customTypography.buttonText
                )
            }
        }
    }
}

//getting string resource by priority value
@Composable
private fun stringPriorityResource(priority: Priority) : String = when (priority) {
    Priority.HIGH -> stringResource(R.string.priority_high)
    Priority.MEDIUM -> stringResource(R.string.priority_common)
    Priority.LOW -> stringResource(R.string.priority_low)
}
fun stringDate(dateInMillis : Long?) : String {
    if (dateInMillis == null) return ""
    else {
        val sdf = SimpleDateFormat("dd MMM yyyy", Locale("ru"))
        return sdf.format(dateInMillis)
    }
}