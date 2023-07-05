package com.example.todoapp.vm

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.todoapp.Revision
import com.example.todoapp.api.NetworkConnectivityObserver
import com.example.todoapp.repository.TaskDatabase
import com.example.todoapp.repository.TodoRepository
import com.example.todoapp.storage.TodoItemData
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.UUID

class TodoViewModel(private val application : Application) : AndroidViewModel(application) {

    private var connectivityObserver = NetworkConnectivityObserver(application.applicationContext)

    private val taskDao = TaskDatabase.getDatabase(application).taskDao()
    val repo : TodoRepository

    init {
        repo = TodoRepository(taskDao, application.applicationContext)
        if (hasInternetConnection()) {
            syncro()
        }
        viewModelScope.launch {
            var status = connectivityObserver.observe().collect()
            Toast.makeText(application.applicationContext, status.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    fun add(todoItemDB: TodoItemData) {
        viewModelScope.launch {
            repo.addItem(todoItemDB)
        }
    }

    fun delete(todoItemDB: TodoItemData) {
        viewModelScope.launch {
            repo.deleteItem(todoItemDB, hasInternetConnection())
        }
    }

    fun change(todoItemDB: TodoItemData) {
        viewModelScope.launch {
            repo.updateItem(todoItemDB, hasInternetConnection())
        }
    }

    fun syncro() {
        viewModelScope.launch {
            val state = repo.syncItemsFromRemote()
            Log.d("STATE", state.code.toString())
        }
    }
/*
    fun getTodoItemById(id : String) : TodoItemData? {
        var item : TodoItemData? = null
        viewModelScope.launch {
            item = tasks.value?.filter { it.id == id }!!.toList()[0]
        }
        return item
    }*/

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