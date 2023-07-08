package com.example.todoapp.model

import com.google.gson.annotations.SerializedName

enum class Priority {
    @SerializedName("low") LOW,
    @SerializedName("basic") MEDIUM,
    @SerializedName("important") HIGH
}