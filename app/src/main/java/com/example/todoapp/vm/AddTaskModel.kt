package com.example.todoapp.vm

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoapp.adapter.areTodoItemsEqual
import com.example.todoapp.api.ConnectivityObserver
import com.example.todoapp.api.NetworkConnectivityObserver
import com.example.todoapp.data.State
import com.example.todoapp.data.TodoRepository
import com.example.todoapp.model.Priority
import com.example.todoapp.model.TodoItemData
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddTaskModel @Inject constructor(
    val application : Application,
    val todoRepository: TodoRepository,
    val connectivityObserver: NetworkConnectivityObserver) : ViewModel() {

    private lateinit var state : State

    var stateTodoItem : MutableState<TodoItemData?> = mutableStateOf(null)
    var stateInit = mutableStateOf(false)
    private var status : ConnectivityObserver.Status = ConnectivityObserver.Status.Unavailable

    fun initLifeDataForChange(todoItemId : String?) {
        if (todoItemId != null) {
            val _todoItemData = getTodoItemById(todoItemId)
            Log.d("Id", todoItemId.toString())
            if (_todoItemData != null && stateInit.value == false) {
                stateTodoItem.value = _todoItemData.copy()
                stateInit.value = true
            }
        }
        else {
            stateTodoItem = mutableStateOf(null)
        }
    }

    init {
        connectivityObserver.observe().onEach {
            status = it
        }.launchIn(viewModelScope)
    }

    fun addOrUpdate(todoItemDB: TodoItemData) {
        viewModelScope.launch {
            if (stateTodoItem.value == null) {
                add(todoItemDB)
            }
            else if (!areTodoItemsEqual(todoItemDB, stateTodoItem.value!!)) {
                change(todoItemDB)
            }
        }
    }
    private fun add(todoItemDB: TodoItemData) {
        viewModelScope.launch {
            state = todoRepository.addItem(todoItemDB, hasInternetConnection())
            Toast.makeText(application.applicationContext, notifyState(state, "добавлена"), Toast.LENGTH_SHORT).show()
        }
    }

    fun delete(todoItemDB: TodoItemData) {
        viewModelScope.launch {
            state = todoRepository.deleteItem(todoItemDB, hasInternetConnection())
            Toast.makeText(application.applicationContext, notifyState(state, "удалена"), Toast.LENGTH_SHORT).show()
        }
    }

    private fun change(todoItemDB: TodoItemData) {
        viewModelScope.launch {
            state = todoRepository.updateItem(todoItemDB, hasInternetConnection())
            Toast.makeText(application.applicationContext, notifyState(state, "изменена"), Toast.LENGTH_SHORT).show()
        }
    }

    fun getTodoItemById(id : String) : TodoItemData? {
        var item : TodoItemData? = null
        viewModelScope.launch {
            item = todoRepository.todoList.value?.filter { it.id == id }!!.toList()[0]
        }
        return item
    }

    fun notifyState(state : State, temp : String) : String{
        when(state) {
            State.Success -> return "Задача $temp"
            State.AbleToSync -> return "Выполнено, необходима синхронизация"
            else -> return "Задача не $temp, ошибка"
        }
    }

    private fun hasInternetConnection() : Boolean {
        return status == ConnectivityObserver.Status.Available
    }
}