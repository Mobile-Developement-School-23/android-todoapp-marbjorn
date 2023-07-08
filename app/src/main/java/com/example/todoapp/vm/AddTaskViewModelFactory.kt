package com.example.todoapp.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

class AddTaskModelFactory @Inject constructor(
        modelProvider: Provider<AddTaskModel>
    ) : ViewModelProvider.Factory {

        private val providers = mapOf<Class<*>, Provider<out ViewModel>>(
            AddTaskModel::class.java to modelProvider
        )
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AddTaskModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return providers[modelClass]!!.get() as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }