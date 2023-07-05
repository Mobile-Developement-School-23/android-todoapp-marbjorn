package com.example.todoapp.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.todoapp.Revision
import com.example.todoapp.api.NetworkResponse
import com.example.todoapp.api.RetrofitInstance
import com.example.todoapp.api.TodoItemWrapper
import com.example.todoapp.api.safeApiCall
import com.example.todoapp.storage.TodoItemData
import com.example.todoapp.storage.TodoListData

class TodoRepository(val taskDao : TaskDao, context : Context) {


    private var _todoList : MutableLiveData<List<TodoItemData>>
    val todoList : LiveData<List<TodoItemData>>
    private val prefs = Revision(context)

    init {
        _todoList = MutableLiveData<List<TodoItemData>>(emptyList())
        todoList = _todoList
        getLocalTasks()

    }
    suspend fun rewriteItemsFromRemote() : State {
        val response = safeApiCall { RetrofitInstance.api.getListOfItems() }
        when (response) {
            is NetworkResponse.Success -> {
                deleteAll()
                for (i in response.data.list) {
                    addItem(i)
                }
                Log.d("Rewrite Success", response.toString())
                return State.Success
            }
            is NetworkResponse.Error -> {
                Log.d("Rewrite Network", response.toString())
                return stateGenerator(response.code!!)
            }
            else -> {
                Log.d("Rewrite Error", response.toString())
                return State.OtherProblem
            }
        }
    }

    suspend fun syncItemsFromRemote() : State {
        val _revision = prefs.getRevision()
        val listFromDb = TodoListData(
            list = taskDao.getAllTasksAsList(),
            revision = _revision
        )
        val response = safeApiCall {
            RetrofitInstance.api.patchListOfItems(_revision, listFromDb )
        }

        when (response) {
            is NetworkResponse.Success -> {
                prefs.setRevision(response.data.revision!!)
                deleteAll()
                for (i in response.data.list) {
                    addItem(i)
                }
                Log.d("Sync Success", response.toString())
                return State.Success
            }
            is NetworkResponse.Error -> {
                Log.d("Sync Network Error", response.toString())
                return stateGenerator(response.code!!)
            }
            else -> {
                Log.d("Sync Error", response.toString())
                return State.OtherProblem
            }
        }
    }

    suspend fun addItem(todoItem : TodoItemData, hasInternetConntection: Boolean = false) : State {
        val _revision = prefs.getRevision()
        val wrapper = TodoItemWrapper(
            element = todoItem,
            revision = _revision
        )
        addItem(todoItem)
        if (hasInternetConntection) {
            val response = safeApiCall { RetrofitInstance.api.addItem(_revision, wrapper) }
            when (response) {
                is NetworkResponse.Success -> {
                    prefs.setRevision(response.data.revision!!)
                    Log.d("AddItem Success", response.toString())
                    return State.Success
                }
                is NetworkResponse.Error -> {
                    Log.d("AddItem Network Error", response.toString())
                    return stateGenerator(response.code!!)
                }
                else -> {
                    Log.d("AddItem Error", response.toString())
                    return stateGenerator(null)
                }
            }
        }
        return State.Success
    }

    suspend fun updateItem(todoItem: TodoItemData, hasInternetConnection: Boolean = false) : State {
        val wrapper = TodoItemWrapper(
            element = todoItem
        )
        addItem(todoItem)
        if (hasInternetConnection) {
            val response = safeApiCall { RetrofitInstance.api.changeItem(prefs.getRevision(), todoItem.id, wrapper) }
            when (response) {
                is NetworkResponse.Success -> {
                    prefs.setRevision(response.data.revision!!)
                    Log.d("UpdateItem Success", response.toString())
                    return State.Success
                }
                is NetworkResponse.Error -> {
                    Log.d("UpdateItem Network Error", response.toString())
                    return stateGenerator(response.code!!)
                }
                else -> {
                    Log.d("UpdateItem Error", response.toString())
                    return State.OtherProblem
                }
            }
        }
        return State.Success
    }

    suspend fun deleteItem(todoItem: TodoItemData, hasInternetConnection: Boolean = false) : State {
        val wrapper = TodoItemWrapper(
            element = todoItem
        )
        deleteItem(todoItem)
        if (hasInternetConnection) {
            val response = safeApiCall { RetrofitInstance.api.deleteItem(prefs.getRevision(), todoItem.id) }
            when (response) {
                is NetworkResponse.Success -> {
                    prefs.setRevision(response.data.revision!!)
                    Log.d("UpdateItem Success", response.toString())
                    return State.Success
                }
                is NetworkResponse.Error -> {
                    Log.d("Sync Network Error", response.toString())
                    return stateGenerator(response.code!!)
                }
                else -> {
                    Log.d("UpdateItem Error", response.toString())
                    return State.OtherProblem
                }
            }
        }
        return State.Success
    }

    fun checkRevision(localRevision: Int, remoteRevision: Int) : State = when(remoteRevision) {
        localRevision -> State.Success
        localRevision + 1 -> {
            prefs.setRevision(remoteRevision)
            State.Success
        }

        else -> State.AbleToSync
    }

    //Room DB wrapper functions
    fun getLocalTasks() {
        _todoList.value = taskDao.getAllTasksAsList()
    }
    suspend fun addItem(todoEntity: TodoItemData) = taskDao.insertItem(todoEntity)
    // suspend fun update(todoEntity: TodoItemData) = taskDao.updateItem(todoEntity)
    suspend fun countId() : Int = taskDao.itemsCount()
    suspend fun deleteAll() = taskDao.deleteAll()
    suspend fun deleteItem(todoEntity: TodoItemData) = taskDao.deleteItem(todoEntity)

}