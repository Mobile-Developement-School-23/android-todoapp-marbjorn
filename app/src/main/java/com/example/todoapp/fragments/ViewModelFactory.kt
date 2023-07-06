package com.example.todoapp.fragments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.api.NetworkConnectivityObserver
import com.example.todoapp.data.TodoRepository
import com.example.todoapp.vm.AddTaskModel
import com.example.todoapp.vm.TodoViewModel

class ViewModelFactory(
    private val todoRepository: TodoRepository,
    private var connectivityObserver : NetworkConnectivityObserver
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when (modelClass) {
        TodoViewModel::class.java -> TodoViewModel(
            todoRepository,
            connectivityObserver
        )
        AddTaskModel::class.java -> AddTaskModel(
            todoRepository,
            connectivityObserver
        )
        else -> throw IllegalArgumentException("${modelClass.simpleName} cannot be provided.")
    } as T
}