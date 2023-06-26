package com.example.todoapp.storage

import java.util.Date

enum class Priority {
    LOW, MEDIUM, HIGH
}

data class TodoItem(var id : String,
                    var text : String,
                    var priority: Priority = Priority.MEDIUM,
                    var isDone : Boolean = false,
                    var creationDate: Date,
                    var deadlineDate: Date? = null,
                    var changeDate: Date? = null
                    )
