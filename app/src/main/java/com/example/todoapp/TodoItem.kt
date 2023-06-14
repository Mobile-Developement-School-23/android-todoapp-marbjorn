package com.example.todoapp

import java.util.Date

enum class Priority {
    LOW, MEDIUM, HIGH
}

data class TodoItem(val id : String,
                    val text : String,
                    val priority: Priority,
                    val isDone : Boolean,
                    val creationDate: Date,
                    val deadlineDate: Date?,
                    val changeDate: Date?
                    )
