package com.example.todoapp.fragments.addtask

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
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
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.todoapp.R
import com.example.todoapp.fragments.addtask.theme.spacings
import com.example.todoapp.model.Priority
import com.example.todoapp.model.TodoItemData
import com.example.todoapp.vm.AddTaskEvent
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.Calendar
import java.util.Locale
import java.util.UUID

@ExperimentalMaterial3Api
@Composable
fun AddTaskScreen (
    initialTodoItem: MutableState<TodoItemData?> = mutableStateOf( null ),
    isInitialTodoItemInitialized: MutableState<Boolean> = mutableStateOf(false),
    onEvent: (AddTaskEvent, TodoItemData) -> Unit = { _, _ ->},
    navController: NavController = NavController(LocalContext.current)
) {
    var dropdownMenuExpanded by rememberSaveable { mutableStateOf(false) }
    val textValue = remember { mutableStateOf("") }
    var priorityValue by remember { mutableStateOf(Priority.MEDIUM) }

    var switchValue by remember { mutableStateOf(false) }

    val nonEmptyString = stringResource(id = R.string.deadline_not_selected)
    val deadlineValueState = rememberMaterialDialogState()
    val deadlineDate = remember {
        mutableStateOf<Long?>(null)
    }

    val deadlineString = remember { mutableStateOf("") }

    var deleteButtonEnabled by remember {
        mutableStateOf(false)
    }


    val verticalScrollState = rememberScrollState()
    val alpha: Int by animateIntAsState(if (verticalScrollState.value > 0) 12 else 0)

    //initializing add task to change item
    if (!isInitialTodoItemInitialized.value && initialTodoItem.value != null) {
        Log.d("Id", initialTodoItem.toString())
        deleteButtonEnabled = true
        switchValue = initialTodoItem.value!!.deadline != null
        textValue.value = initialTodoItem.value!!.text
        priorityValue = initialTodoItem.value!!.importance
        deadlineDate.value = initialTodoItem.value!!.deadline
        deadlineString.value = dateToString(deadlineDate.value, switchValue, nonEmptyString)
        isInitialTodoItemInitialized.value = true
    }


    CustomDatePickerDialog(
        deadlineValueState,
        deadlineDate,
        deadlineString,
        switchValue,
        nonEmptyString
    )

    Scaffold(
        topBar = {
            Surface(
                shadowElevation = alpha.dp,
            ) {
                TopAppBar(
                    title = {},
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    navigationIcon = {
                        IconButton(onClick = {   //close button
                            navController.navigate(R.id.action_addTaskFragment_to_todoListFragment)
                        }) {
                            Icon(painterResource(id = R.drawable.round_close_24), null)
                        }
                    },
                    actions = {
                        PressIconButton(
                            onClick = {
                                onEvent(
                                    AddTaskEvent.AddOrUpdate,
                                    TodoItemData(
                                        id = if (initialTodoItem.value == null)
                                            UUID.randomUUID().toString()
                                        else initialTodoItem.value!!.id,
                                        text = textValue.value,
                                        importance = priorityValue,
                                        deadline = deadlineDate.value,
                                        createdAt = if (initialTodoItem.value != null)
                                            initialTodoItem.value!!.createdAt
                                        else Calendar.getInstance().timeInMillis,
                                        changedAt = Calendar.getInstance().timeInMillis
                                    )
                                )
                                navController.navigate(R.id.action_addTaskFragment_to_todoListFragment)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent
                            ),
                            text = {
                                Text(
                                    text = stringResource(id = R.string.save).uppercase(),
                                    color = colorResource(id = R.color.blue),
                                    style = MaterialTheme.typography.labelMedium
                                )
                            },
                            icon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_save_24),
                                    contentDescription = null,
                                    tint = colorResource(id = R.color.blue)
                                )
                            }
                        )
                    })
            }
        }) {
        Column(
            modifier = Modifier
                .verticalScroll(verticalScrollState)
                .padding(it)
                .padding(
                    top = MaterialTheme.spacings.standartPadding,
                    start = MaterialTheme.spacings.standartPadding,
                    end = MaterialTheme.spacings.standartPadding
                )
        ) {

            TextField(textValue)

            //section with priority
            Column(
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 10.dp)
                    .fillMaxWidth()
                    .clickable { dropdownMenuExpanded = true }
            ) {
                Text( //Importance label
                    stringResource(id = R.string.importance),
                    style = MaterialTheme.typography.headlineMedium
                )
                DropdownMenu( //Dropdown menu
                    expanded = dropdownMenuExpanded,
                    onDismissRequest = { dropdownMenuExpanded = false },
                    modifier = Modifier.padding(MaterialTheme.spacings.standartPadding)
                ) {
                    Text(stringResource(
                        id = R.string.priority_high
                    ),
                        color = colorResource(id = R.color.red),
                        modifier = Modifier
                            .padding(bottom = MaterialTheme.spacings.smallPadding)
                            .clickable {
                                priorityValue = Priority.HIGH
                                dropdownMenuExpanded = false
                            }
                    )
                    Text(stringResource(
                        id = R.string.priority_common
                    ),
                        modifier = Modifier
                            .padding(bottom = MaterialTheme.spacings.smallPadding)
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
                    style = MaterialTheme.typography.headlineSmall
                )

            }

            CustomDivider()

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
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Text(deadlineString.value,
                        style = MaterialTheme.typography.headlineSmall,
                        color = colorResource(id = R.color.blue))
                }
                //spacing between subsection and switch
                Spacer(Modifier.weight(1f))

                //deadline enable switch
                Switch(
                    checked = switchValue,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = colorResource(id = R.color.blue),
                        checkedTrackColor = colorResource(id = R.color.gray_light),
                        uncheckedTrackColor = colorResource(id = R.color.gray_light)
                    ),
                    onCheckedChange = {
                        switchValue = it
                        if (!switchValue) {
                            deadlineDate.value = null
                        }
                        deadlineString.value = dateToString(deadlineDate.value, switchValue, nonEmptyString)
                    },

                    modifier = Modifier.padding(end = 10.dp)
                )
            }

            CustomDivider()

            //delete button
            PressIconButton(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = colorResource(id = R.color.red),
                    disabledContentColor = MaterialTheme.colorScheme.onSecondary
                ),
                onClick = {
                    if (initialTodoItem.value != null) {
                        onEvent(AddTaskEvent.Delete, initialTodoItem.value!!)
                    }
                    navController.navigate(R.id.action_addTaskFragment_to_todoListFragment)
                    } ,
                icon = {
                    Icon(
                    painter = painterResource(id = R.drawable.baseline_delete_24),
                    contentDescription = null,
                    modifier = Modifier.padding(end = MaterialTheme.spacings.smallPadding)
                )},
                text = {
                    Text(
                    text = stringResource(id = R.string.delete).uppercase(),
                    style = MaterialTheme.typography.labelMedium)
                }
                )
            }
        }
    }

@Preview
@Composable  //text field
private fun TextField(textValue : MutableState<String> = mutableStateOf("")) {
    OutlinedTextField(
        value = textValue.value,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            focusedBorderColor = MaterialTheme.colorScheme.primaryContainer,
            unfocusedBorderColor = MaterialTheme.colorScheme.primaryContainer,
            focusedTextColor = MaterialTheme.colorScheme.onPrimary,
            unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
            cursorColor = MaterialTheme.colorScheme.onSecondary
        ),
        onValueChange = { textValue.value = it },
        placeholder = {
            Text(
                stringResource(id = R.string.todo_edit_hint),
                color = colorResource(id = R.color.gray_light)
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(
                minHeight = 100.dp
            )
            .offset(y = 3.dp) //shifting shadow to bottom side
            .shadow(
                elevation = 5.dp,
                clip = false,
            )
            .offset(y = (-3).dp)
    )

}



@Composable
fun CustomDatePickerDialog(
    deadlineValueState : MaterialDialogState = rememberMaterialDialogState(),
    deadlineDate : MutableState<Long?> = mutableStateOf(null),
    deadlineString : MutableState<String> = mutableStateOf(""),
    isDialogEnable : Boolean = true,
    msgNotSelected : String = ""
) {
    MaterialDialog(
        dialogState = deadlineValueState,
        backgroundColor = MaterialTheme.colorScheme.secondary,
        buttons = {
            positiveButton(text = "Ok", textStyle = MaterialTheme.typography.labelMedium)
            negativeButton(text = "Cancel", textStyle = MaterialTheme.typography.labelMedium)
        }
    ) {
        datepicker(
            colors = DatePickerDefaults.colors(
                headerBackgroundColor = colorResource(id = R.color.blue),
                dateActiveBackgroundColor = colorResource(id = R.color.blue),
                dateActiveTextColor = MaterialTheme.colorScheme.onSecondary,
                dateInactiveTextColor = MaterialTheme.colorScheme.onSecondary,
                calendarHeaderTextColor = MaterialTheme.colorScheme.onSecondary
            ),
            initialDate = if (deadlineDate.value == null) LocalDate.now()
            else Instant.ofEpochMilli(deadlineDate.value!!)
                .atZone(ZoneId.systemDefault()).toLocalDate()
        ) {
            deadlineDate.value = it.atStartOfDay().toEpochSecond(ZoneOffset.UTC)*1000
            deadlineString.value = dateToString(deadlineDate.value, isDialogEnable, msgNotSelected)
        }
    }
}

//animated button
@Preview
@Composable
fun PressIconButton(
    modifier: Modifier = Modifier,
    colors : ButtonColors = ButtonDefaults.buttonColors(),
    onClick: () -> Unit = {},
    icon: @Composable () -> Unit = {},
    text: @Composable () -> Unit = {},
    interactionSource: MutableInteractionSource =
        remember { MutableInteractionSource() },
) {
    val isPressed by interactionSource.collectIsPressedAsState()
    val shift by animateDpAsState(targetValue = if (isPressed) 3.dp else 0.dp)
    Button(onClick = onClick, modifier = modifier, colors = colors,
        interactionSource = interactionSource) {
        AnimatedVisibility(
            visible = isPressed) {
            Row {
                icon()
                Spacer(Modifier.size(shift))
            }
        }
        text()
    }
}


@Composable
private fun CustomDivider() {
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        color = colorResource(id = R.color.support_separator)
    )
}

//getting string resource by priority value
@Composable
private fun stringPriorityResource(priority: Priority) : String = when (priority) {
    Priority.HIGH -> stringResource(R.string.priority_high)
    Priority.MEDIUM -> stringResource(R.string.priority_common)
    Priority.LOW -> stringResource(R.string.priority_low)
}




fun dateToString(dateInMillis : Long?,
                 isStringNonEmpty : Boolean = false,
                 resourceString : String = "") : String {
    return if (dateInMillis == null)
        if (!isStringNonEmpty) ""
        else resourceString
    else {
        val sdf = SimpleDateFormat("dd MMM yyyy", Locale("ru"))
        sdf.format(dateInMillis)
    }
}
