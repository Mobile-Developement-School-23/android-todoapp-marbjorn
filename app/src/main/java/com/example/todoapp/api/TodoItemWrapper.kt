package com.example.todoapp.api

import com.example.todoapp.storage.TodoItemData
import com.google.gson.annotations.SerializedName

data class TodoItemWrapper (
    @SerializedName("status") var status : String,
    @SerializedName("element") var element : TodoItemData,
    @SerializedName("revision") var revision : String? = null
)