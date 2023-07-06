package com.example.todoapp.storage

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


enum class Priority {
    @SerializedName("low") LOW,
    @SerializedName("basic") MEDIUM,
    @SerializedName("important") HIGH
}

@Parcelize
@Entity(tableName = "task_table")
@OnConflictStrategy()
data class TodoItemData(
    @PrimaryKey @SerializedName("id") var id : String,
    @SerializedName("text") var text : String,
    @SerializedName("importance") var importance : Priority = Priority.MEDIUM,
    @SerializedName("deadline") var deadline : Long? = null,
    @SerializedName("done") var done : Boolean = false,
    @SerializedName("color") var color : String? = null,
    @SerializedName("created_at") var createdAt : Long,
    @SerializedName("changed_at") var changedAt : Long,
    @SerializedName("last_updated_by") var lastUpdatedBy : String = "1"
) : Parcelable


data class TodoListData(
    @SerializedName("status") var status : String = "ok",
    @SerializedName("list") var list : List<TodoItemData>,
    @SerializedName("revision") var revision : Int? = null
)