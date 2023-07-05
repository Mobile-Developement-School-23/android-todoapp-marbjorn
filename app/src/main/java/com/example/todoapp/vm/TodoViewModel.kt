package com.example.todoapp.vm

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.todoapp.api.NetworkResponse
import com.example.todoapp.api.RetrofitInstance
import com.example.todoapp.repository.TaskDatabase
import com.example.todoapp.repository.TodoRepository
import com.example.todoapp.storage.TodoItemData
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TodoViewModel(private val application : Application) : AndroidViewModel(application) {
    private val taskDao = TaskDatabase.getDatabase(application).taskDao()
    private val repo : TodoRepository

    lateinit var tasks : LiveData<List<TodoItemData>>

    init {
        repo = TodoRepository(taskDao)
        if (hasInternetConnection()) {
            viewModelScope.launch {
                //repo.patchItems()
            }
        }
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
        if (hasInternetConnection()) {
            viewModelScope.launch {
                tasks = repo.patchItems()
            }
        }
        else {
            Log.e("API", "Нет интернета")
        }
    }

    fun getTodoItemById(id : String) : TodoItemData? {
        var item : TodoItemData? = null
        viewModelScope.launch {
            item = tasks.value?.filter { it.id == id }!!.toList()[0]
        }
        return item
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = application.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }


}