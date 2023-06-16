package com.example.todoapp

import android.content.Context
import java.util.*



class TodoRepository {
    private var items : MutableList<TodoItem>
    init {
        items = mutableListOf<TodoItem>(
            TodoItem("1",
                "приготовить поесть",
                Priority.LOW,
                false,
                Date(2023, 6, 1),
                null,
                null),
            TodoItem("2",
                "поесть",
                Priority.MEDIUM,
                true,
                Date(2023, 6, 1),
                null,
                null),
            TodoItem("3",
                "Суперультрамегаважное дело, очень сильно нереально важное дело, просто катастрофически, я бы даже сказал кошмарно важное-преважное международной важности дело ",
                Priority.MEDIUM,
                false,
                Date(2023, 6, 1),
                null,
                null),
            TodoItem("4",
                "посмотреть телик",
                Priority.HIGH,
                true,
                Date(2023, 6, 1),
                null,
                null),
            TodoItem("5",
                "забрать 30 кг бетона",
                Priority.HIGH,
                true,
                Date(2023, 6, 1),
                null,
                null),
            TodoItem("6",
                "поспать жоска",
                Priority.LOW,
                false,
                Date(2023, 6, 1),
                null,
                null),
            TodoItem("7",
                "купить что-нить",
                Priority.LOW,
                true,
                Date(2023, 6, 1),
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
    fun getTodoItems(context : Context) : List<TodoItem> = items
}