package com.example.todoapp.model

import com.example.todoapp.model.TodoItemData
import com.google.gson.annotations.SerializedName


data class TodoListWrapper(
    @SerializedName("status") var status : String = "ok",
    @SerializedName("list") var list : List<TodoItemData>,
    @SerializedName("revision") var revision : Int? = null
)