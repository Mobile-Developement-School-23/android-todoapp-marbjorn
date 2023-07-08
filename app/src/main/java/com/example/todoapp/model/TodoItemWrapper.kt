package com.example.todoapp.model

import com.google.gson.annotations.SerializedName

data class TodoItemWrapper(
    @SerializedName("status") var status: String = "ok",
    @SerializedName("element") var element: TodoItemData,
    @SerializedName("revision") var revision: Int? = null
)