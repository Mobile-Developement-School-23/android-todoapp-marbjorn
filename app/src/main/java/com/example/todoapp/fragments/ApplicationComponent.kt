package com.example.todoapp.fragments

import android.app.Application
import com.example.todoapp.api.NetworkConnectivityObserver
import com.example.todoapp.data.TaskDatabase
import com.example.todoapp.data.TodoRepository

class ApplicationComponent(application: Application) {
    private val taskDatabase = TaskDatabase.getDatabase(application.applicationContext)
    private val todoRepository = TodoRepository(taskDatabase.taskDao(), application)
    private var connectivityObserver = NetworkConnectivityObserver(application.applicationContext)
    val viewModelFactory = ViewModelFactory(todoRepository, connectivityObserver)
}
