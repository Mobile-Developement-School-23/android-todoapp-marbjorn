package com.example.todoapp.model

import androidx.room.TypeConverter
import com.example.todoapp.model.Priority

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