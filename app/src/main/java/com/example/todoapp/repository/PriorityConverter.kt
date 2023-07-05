package com.example.todoapp.repository

import androidx.room.TypeConverter
import com.example.todoapp.storage.Priority

class PriorityConverter {
    @TypeConverter
    fun toPriority(value: String) = when(value) {
        "low" -> Priority.LOW
        "basic" -> Priority.MEDIUM
        "important" -> Priority.HIGH
        else -> Priority.MEDIUM
    }

    @TypeConverter
    fun fromPriority(value: Priority) = when(value) {
        Priority.LOW -> "low"
        Priority.MEDIUM -> "basic"
        Priority.HIGH -> "important"
    }
}