package com.example.todoapp.repository

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.todoapp.api.RetrofitInstance
import com.example.todoapp.api.TodoItemWrapper
import com.example.todoapp.storage.TodoItemData
import com.example.todoapp.storage.TodoListData


class TodoRepository(val taskDao : TaskDao) {

    var REVISION : String = "REVISION"
    var lastRevision : Int = 0
    suspend fun addItem(todoEntity: TodoItemData) = taskDao.addItem(todoEntity)

    suspend fun update(todoEntity: TodoItemData) = taskDao.update(todoEntity)

    suspend fun deleteAll() = taskDao.deleteAll()

    suspend fun deleteItem(todoEntity: TodoItemData) = taskDao.deleteItem(todoEntity)

    fun getAllTask() : LiveData<List<TodoItemData>> = taskDao.getAllTasks()


    suspend fun postItem(todoEntity: TodoItemData) {
        val wrapper = TodoItemWrapper(
            status = "ok",
            element = todoEntity,
            revision = lastRevision
        )

    }

    suspend fun patchItems() : LiveData<List<TodoItemData>> {
        var list : List<TodoItemData>? = getAllTask().value
        Log.e("SIZE", getAllTask().value?.size.toString())
        if (list == null) {
            list = listOf()
        }
        val todoListData = TodoListData(
            list = list
        )
        val newList = RetrofitInstance.api.patchListOfItems(lastRevision.toString(), todoListData)
        deleteAll()
        Log.e("SIZE", newList.list.size.toString())
        for (i in newList.list) {
            addItem(i)
        }
        lastRevision = newList.revision!!
        Log.e("SIZE", getAllTask().value?.size.toString())
        return getAllTask()
    }

    suspend fun putItem(todoEntity: TodoItemData) {
        val wrapper = TodoItemWrapper(
            element = todoEntity,
            revision = lastRevision
        )

    }



}