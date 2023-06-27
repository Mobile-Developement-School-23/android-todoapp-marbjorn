package com.example.todoapp.api

import android.graphics.Color
import com.google.gson.annotations.SerializedName
import java.util.UUID

data class TodoItemData(
    @SerializedName("id") var id : String,
    @SerializedName("text") var text : String,
    @SerializedName("importance") var importance : String,
    @SerializedName("deadline") var deadline : Long? = null,
    @SerializedName("done") var done : Boolean,
    @SerializedName("color") var color : String? = null,
    @SerializedName("created_at") var createdAt : Long,
    @SerializedName("changed_at") var changedAt : Long,
    @SerializedName("last_updated_by") var lastUpdatedBy : String
)

data class TodoListData(
    @SerializedName("status") var status : String,
    @SerializedName("list") var list : List<TodoItemData>,
    @SerializedName("revision") var revision : Int? = null
)


/*
* {
      "id": <uuid>, // уникальный идентификатор элемента
      "text": "blablabla",
      "importance": "<importance>", // importance = low | basic | important
      "deadline": <unix timestamp>, // int64, может отсутствовать, тогда нет
      "done": <bool>,
      "color": "#FFFFFF", // может отсутствовать
      "created_at": <unix timestamp>,
      "changed_at": <unix timestamp>,
      "last_updated_by": <device id>
    }*/