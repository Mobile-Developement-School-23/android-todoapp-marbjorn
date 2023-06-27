package com.example.todoapp.repository

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.

@Parcelize
@Entity (tableName = "task_table")
data class TaskEntity (
    @PrimaryKey var id : String,
    var text : String,
    var importance : String,
    var deadline : Long? = null,
    var done : Boolean,
    var color : String? = null,
    var createdAt : Long,
    var changedAt : Long,
    var lastUpdatedBy : String
)