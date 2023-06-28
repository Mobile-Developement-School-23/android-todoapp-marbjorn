package com.example.todoapp.repository

import androidx.lifecycle.LiveData
import com.example.todoapp.storage.TodoItemData


class TodoRepository(val taskDao : TaskDao) {

    var lastRevision : Int = 0
    suspend fun addItem(todoEntity: TodoItemData) = taskDao.addItem(todoEntity)

    suspend fun update(todoEntity: TodoItemData) = taskDao.update(todoEntity)

    suspend fun deleteAll() = taskDao.deleteAll()

    suspend fun deleteItem(todoEntity: TodoItemData) = taskDao.deleteItem(todoEntity)

    fun getAllTask() : LiveData<List<TodoItemData>> = taskDao.getAllTasks()


}
/*

    private var items : MutableList<TodoItem>
    init {
    }

    fun addTodoItem( item : TodoItem) {
        items.add(item)
    }

    fun deleteTodoItem ( id : String) {
        val itemToDelete = items.find { it.id == id }
        items.remove(itemToDelete)
    }

    fun getTodoItem( id : String ) : TodoItem? {
        val itemToGet = items.find { it.id == id }
        return itemToGet
    }
    fun getTodoItems() : List<TodoItem> = items


private val textList = mutableListOf(
    TodoItem("a",
        "Приготовить поесть",
        Priority.LOW,
        false,
        Date(2023, 6, 1),
        null,
        null),
    TodoItem("b",
        "Поесть",
        Priority.MEDIUM,
        true,
        Date(2023, 6, 1),
        null,
        null),
    TodoItem("c",
        "Суперультрамегаважное дело, очень сильно нереально важное дело, просто катастрофически, я бы даже сказал кошмарно важное-преважное международной важности дело ",
        Priority.MEDIUM,
        false,
        Date(2023, 6, 1),
        Date(2023, 10, 12),
        null),
    TodoItem("d",
        "Посмотреть телик",
        Priority.HIGH,
        true,
        Date(2023, 5, 12),
        null,
        null),
    TodoItem("e",
        "забрать 30 кг бетона",
        Priority.HIGH,
        true,
        Date(2023, 3, 14),
        null,
        null),
    TodoItem("f",
        "поспать жоска",
        Priority.LOW,
        false,
        Date(2022, 1, 2),
        null,
        null),
    TodoItem("j",
        "купить что-нить",
        Priority.LOW,
        true,
        Date(2022, 12, 17),
        null,
        null),

    )*/