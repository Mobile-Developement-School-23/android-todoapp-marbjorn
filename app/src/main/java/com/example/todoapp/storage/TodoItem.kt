package com.example.todoapp.storage

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.Date

enum class Priority {
    LOW, MEDIUM, HIGH
}

data class TodoItem(var id : String,
                    var text : String,
                    var priority: Priority = Priority.MEDIUM,
                    var done : Boolean = false,
                    var createdAt: Date,
                    var deadline: Date? = null,
                    var changedAt: Date? = null
                    )


//@Parcelize
@Entity(tableName = "task_table")
data class TodoItemData(
    @PrimaryKey @SerializedName("id") var id : String,
    @SerializedName("text") var text : String,
    @SerializedName("importance") var importance : String,
    @SerializedName("deadline") var deadline : Long? = null,
    @SerializedName("done") var done : Boolean,
    @SerializedName("color") var color : String? = null,
    @SerializedName("created_at") var createdAt : Long,
    @SerializedName("changed_at") var changedAt : Long,
    @SerializedName("last_updated_by") var lastUpdatedBy : String
) // : Parcelable

data class TodoListData(
    @SerializedName("status") var status : String,
    @SerializedName("list") var list : List<TodoItemData>,
    @SerializedName("revision") var revision : Int? = null
)