package com.example.todoapp.vm

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    val context : Context,
    val todoRepository: TodoRepository,
    val connectivityObserver: NetworkConnectivityObserver) : ViewModel() {

    private lateinit var state : State
    var hasDeadline = MutableLiveData<Boolean>(false)
    var priority = MutableLiveData<Priority>(Priority.MEDIUM)
    var deadlineDate = MutableLiveData<Long?>(null)
    var isInit = MutableLiveData<Boolean>(false)

    private var status : ConnectivityObserver.Status = ConnectivityObserver.Status.Unavailable

    init {
        connectivityObserver.observe().onEach {
            status = it
        }.launchIn(viewModelScope)
    }

    fun add(todoItemDB: TodoItemData) {
        viewModelScope.launch {
            state = todoRepository.addItem(todoItemDB, hasInternetConnection())
            Toast.makeText(context, notifyState(state, "добавлена"), Toast.LENGTH_SHORT).show()
        }
    }

    fun delete(todoItemDB: TodoItemData) {
        viewModelScope.launch {
            state = todoRepository.deleteItem(todoItemDB, hasInternetConnection())
            Toast.makeText(context, notifyState(state, "удалена"), Toast.LENGTH_SHORT).show()
        }
    }

    fun change(todoItemDB: TodoItemData) {
        viewModelScope.launch {
            state = todoRepository.updateItem(todoItemDB, hasInternetConnection())
            Toast.makeText(context, notifyState(state, "изменена"), Toast.LENGTH_SHORT).show()
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