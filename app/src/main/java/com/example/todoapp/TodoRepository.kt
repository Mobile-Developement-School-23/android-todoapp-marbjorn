package com.example.todoapp

import android.content.Context
import androidx.lifecycle.LiveData
import java.util.*



class TodoRepository {
    private var items : MutableList<TodoItem>
    init {
        items = mutableListOf<TodoItem>(
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

        )
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
}