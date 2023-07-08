package com.example.todoapp.vm

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.api.ConnectivityObserver
import com.example.todoapp.api.NetworkConnectivityObserver
import com.example.todoapp.data.State
import com.example.todoapp.data.TodoRepository
import com.example.todoapp.model.TodoItemData
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class TodoViewModel @Inject constructor(
    context : Context,
    val todoRepository: TodoRepository,
    connectivityObserver: NetworkConnectivityObserver) : ViewModel() {


    val listOfItems : LiveData<List<TodoItemData>>

    private var status : ConnectivityObserver.Status = ConnectivityObserver.Status.Unavailable

    private val lifedataMediator = MediatorLiveData<List<TodoItemData>>()
    init {

        val observer = object : Observer<List<TodoItemData>> {
            override fun onChanged(value: List<TodoItemData>) {
                Log.d("Observer", "Something changed")
                lifedataMediator.value = value
            }
        }

        listOfItems = lifedataMediator as LiveData<List<TodoItemData>>

        lifedataMediator.addSource(todoRepository.todoList, observer)

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

    fun syncro() {
        viewModelScope.launch {
            val state = todoRepository.syncItemsFromRemote()
            Log.d("STATE", state.code.toString())
        }
    }

    private fun hasInternetConnection() : Boolean {
        return status == ConnectivityObserver.Status.Available
    }
//    private fun hasInternetConnection(): Boolean {
//        val connectivityManager = application.getSystemService(
//            Context.CONNECTIVITY_SERVICE
//        ) as ConnectivityManager
//        val activeNetwork = connectivityManager.activeNetwork ?: return false
//        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
//        return when {
//            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
//            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
//            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
//            else -> false
//        }
//    }


}