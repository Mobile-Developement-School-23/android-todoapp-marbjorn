package com.example.todoapp.repository

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.todoapp.storage.TodoItemData

@Dao
interface TaskDao {
    @Query("SELECT * FROM task_table ORDER BY changedAt DESC")
    fun getAllTasks() : LiveData<List<TodoItemData>>

    @Delete
    suspend fun deleteItem(todoItemDB: TodoItemData )

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItem(todoItemDB: TodoItemData )

    @Query("DELETE FROM task_table")
    suspend fun deleteAll()

    @Update
    suspend fun update(todoItemDB: TodoItemData )

}