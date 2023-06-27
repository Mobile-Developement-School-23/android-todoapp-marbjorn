package com.example.todoapp.api

import com.google.gson.annotations.SerializedName

data class TodoItemResponseData (
    @SerializedName("status") var status : String,
    @SerializedName("element") var element : TodoItemData,
    @SerializedName("revision") var revision : String? = null
)