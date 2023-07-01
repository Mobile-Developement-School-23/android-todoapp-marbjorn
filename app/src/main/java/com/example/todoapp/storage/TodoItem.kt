package com.example.todoapp.storage

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.Date


enum class Priority {
    @SerializedName("low") LOW,
    @SerializedName("basic") MEDIUM,
    @SerializedName("important") HIGH
}
/*
data class TodoItem(var id : String,
                    var text : String,
                    var priority: Priority = Priority.MEDIUM,
                    var done : Boolean = false,
                    var createdAt: Date,
                    var deadline: Date? = null,
                    var changedAt: Date? = null
                    )
*/
const val deviceId = "1"

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
    @SerializedName("last_updated_by") var lastUpdatedBy : String = deviceId
) : Parcelable


data class TodoListData(
    @SerializedName("status") var status : String = "ok",
    @SerializedName("list") var list : List<TodoItemData>,
    @SerializedName("revision") var revision : Int? = null
)