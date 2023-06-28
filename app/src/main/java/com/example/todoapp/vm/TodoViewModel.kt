package com.example.todoapp.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.todoapp.api.RetrofitInstance
import com.example.todoapp.repository.TaskDatabase
import com.example.todoapp.repository.TodoRepository
import com.example.todoapp.storage.TodoItemData
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class TodoViewModel(application : Application) : AndroidViewModel(application) {
    private val taskDao = TaskDatabase.getDatabase(application).taskDao()
    private val repo : TodoRepository

    var tasks : LiveData<List<TodoItemData>>

    init {
        repo = TodoRepository(taskDao)
        tasks = repo.getAllTask()
    }

    fun add(todoItemDB: TodoItemData) {
        viewModelScope.launch {
            repo.addItem(todoItemDB)
        }
    }

    fun delete(todoItemDB: TodoItemData) {
        viewModelScope.launch {
            repo.deleteItem(todoItemDB)
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            repo.deleteAll()
        }
    }

    fun change(todoItemDB: TodoItemData) {
        viewModelScope.launch {
            repo.update(todoItemDB)
        }
    }

    fun updateDataFromServer() {
        viewModelScope.launch {
            val list = RetrofitInstance.api.getListOfItems()
            repo.lastRevision = list.revision!!
            for (i in list.list) {
                repo.addItem(i)
            }
            viewModelScope.launch {
                tasks = repo.getAllTask()
            }
        }
    }
}