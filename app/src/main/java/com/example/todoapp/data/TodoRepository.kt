package com.example.todoapp.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.todoapp.api.NetworkResponse
import com.example.todoapp.api.TodoApi
import com.example.todoapp.model.TodoItemWrapper
import com.example.todoapp.api.safeApiCall
import com.example.todoapp.model.TodoItemData
import com.example.todoapp.model.TodoListWrapper
import javax.inject.Inject

class TodoRepository @Inject constructor(val taskDao: TaskDao,
                                         val prefs : SharedPrefs,
                                         val api : TodoApi,
                                         context : Context) {

    private var _todoList : MutableLiveData<List<TodoItemData>>
    val todoList : LiveData<List<TodoItemData>>

    init {
        _todoList = MutableLiveData<List<TodoItemData>>(emptyList())
        todoList = _todoList
        getLocalTasks()
    }
    suspend fun rewriteItemsFromRemote() : State {
        val response = safeApiCall { api.getListOfItems() }
        when (response) {
            is NetworkResponse.Success -> {
                if (response.data.revision!! > prefs.getRevision() + 1)
                deleteAllLocal()
                for (i in response.data.list) {
                    addItemLocal(i)
                }

                getLocalTasks()
                Log.d("Rewrite Success", response.toString())
                return State.Success
            }
            is NetworkResponse.Error -> {
                Log.d("Rewrite Network", response.toString())
                return State.stateGenerator(response.code)
            }
            else -> {
                Log.d("Rewrite Error", response.toString())
                return State.OtherProblem
            }
        }
    }

    suspend fun syncItemsFromRemote() : State {
        val _revision = prefs.getRevision()
        rewriteItemsFromRemote()
        val listFromDb = TodoListWrapper(
            list = taskDao.getAllTasksAsList(),
            revision = _revision
        )
        Log.d("List", listFromDb.toString())

        val response = safeApiCall {
            api.patchListOfItems(_revision, listFromDb )
        }

        when (response) {
            is NetworkResponse.Success -> {
                deleteAllLocal()
                prefs.setRevision(response.data.revision!!)
                for (i in response.data.list) {
                    addItemLocal(i)
                }
                getLocalTasks()
                Log.d("Sync Success", response.toString())
                return State.Success
            }
            is NetworkResponse.Error -> {
                Log.d("Sync Network Error", response.toString())
                return State.stateGenerator(response.code)
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
        todoItem.lastUpdatedBy = prefs.getDeviceId()!!
        addItemLocal(todoItem)
        getLocalTasks()
        if (hasInternetConntection) {
            val response = safeApiCall { api.addItem(_revision, wrapper) }
            when (response) {
                is NetworkResponse.Success -> {
                    prefs.setRevision(response.data.revision!!)
                    Log.d("AddItem Success", response.toString())
                    return State.Success
                }
                is NetworkResponse.Error -> {
                    Log.d("AddItem Network Error", response.toString())
                    return State.stateGenerator(response.code)
                }
                else -> {
                    Log.d("AddItem Error", response.toString())
                    return State.stateGenerator(null)
                }
            }
        }
        return State.Success
    }

    suspend fun updateItem(todoItem: TodoItemData, hasInternetConnection: Boolean = false) : State {
        val wrapper = TodoItemWrapper(
            element = todoItem
        )
        todoItem.lastUpdatedBy = prefs.getDeviceId()!!
        addItemLocal(todoItem)
        getLocalTasks()
        if (hasInternetConnection) {
            val response = safeApiCall { api.changeItem(prefs.getRevision(), todoItem.id, wrapper) }
            when (response) {
                is NetworkResponse.Success -> {
                    prefs.setRevision(response.data.revision!!)
                    Log.d("UpdateItem Success", response.toString())
                    return State.Success
                }
                is NetworkResponse.Error -> {
                    Log.d("UpdateItem Network Error", response.toString())
                    Log.d("revision", prefs.getRevision().toString())
                    return State.stateGenerator(response.code)
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
        deleteItemLocal(todoItem)
        getLocalTasks()
        if (!hasInternetConnection) return State.Success
        val response = safeApiCall { api.deleteItem(prefs.getRevision(), todoItem.id) }
        when (response) {
            is NetworkResponse.Success -> {
                prefs.setRevision(response.data.revision!!)
                Log.d("UpdateItem Success", response.toString())
                return State.Success
            }
            is NetworkResponse.Error -> {
                Log.d("Sync Network Error", response.toString())
                return State.stateGenerator(response.code)
            }
            else -> {
                Log.d("UpdateItem Error", response.toString())
                return State.OtherProblem
            }
        }
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
    suspend fun addItemLocal(todoEntity: TodoItemData) = taskDao.insertItem(todoEntity)
    suspend fun countId() : Int = taskDao.itemsCount()
    suspend fun deleteAllLocal() = taskDao.deleteAll()
    suspend fun deleteItemLocal(todoEntity: TodoItemData) = taskDao.deleteItem(todoEntity)

}