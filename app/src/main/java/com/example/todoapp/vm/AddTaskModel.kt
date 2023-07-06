package com.example.todoapp.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.api.NetworkConnectivityObserver
import com.example.todoapp.data.TodoRepository
import com.example.todoapp.storage.Priority
import com.example.todoapp.storage.TodoItemData
import kotlinx.coroutines.launch

class AddTaskModel(
    private var todoRepository: TodoRepository,
    private var connectivityObserver: NetworkConnectivityObserver
) : ViewModel() {
    var hasDeadline = MutableLiveData<Boolean>(false)
    var priority = MutableLiveData<Priority>(Priority.MEDIUM)
    var deadlineDate = MutableLiveData<Long?>(null)
    var isInit = MutableLiveData<Boolean>(false)

    fun setFields(todoItemData: TodoItemData) {
        hasDeadline.value = todoItemData.deadline != null
        priority.value = todoItemData.importance
        hasDeadline.value = todoItemData.deadline != null
        deadlineDate.value = todoItemData.deadline
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

    fun hasInternetConnection() : Boolean = true
}