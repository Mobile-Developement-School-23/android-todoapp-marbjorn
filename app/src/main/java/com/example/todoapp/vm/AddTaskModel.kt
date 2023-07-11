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
    context : Context,
    val todoRepository: TodoRepository,
    val connectivityObserver: NetworkConnectivityObserver) : ViewModel() {

    var hasDeadline = MutableLiveData<Boolean>(false)
    var priority = MutableLiveData<Priority>(Priority.MEDIUM)
    var deadlineDate = MutableLiveData<Long?>(null)
    var isInit = MutableLiveData<Boolean>(false)

    private var status : ConnectivityObserver.Status = ConnectivityObserver.Status.Unavailable

    init {
        connectivityObserver.observe().onEach {
            status = it
            if (it == ConnectivityObserver.Status.Available) {
                val statusResponse = todoRepository.syncItemsFromRemote()
                if (statusResponse == State.Success) {
                    Toast.makeText(context, "Данные обновлены", Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(context,
                        "Возникла ошибка при синхронизации. Данные сохранены локально",
                        Toast.LENGTH_LONG).show()
                }
            }
        }.launchIn(viewModelScope)
    }

    fun add(todoItemDB: TodoItemData) {
        viewModelScope.launch {
            todoRepository.addItem(todoItemDB, hasInternetConnection())
        }
    }

    fun delete(todoItemDB: TodoItemData) {
        viewModelScope.launch {
            todoRepository.deleteItem(todoItemDB, hasInternetConnection())
        }
    }

    fun change(todoItemDB: TodoItemData) {
        viewModelScope.launch {
            todoRepository.updateItem(todoItemDB, hasInternetConnection())
        }
    }

    fun getTodoItemById(id : String) : TodoItemData? {
        var item : TodoItemData? = null
        viewModelScope.launch {
            item = todoRepository.todoList.value?.filter { it.id == id }!!.toList()[0]
        }
        return item
    }

    private fun hasInternetConnection() : Boolean {
        return status == ConnectivityObserver.Status.Available
    }
}