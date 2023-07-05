package com.example.todoapp.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.todoapp.storage.TodoItemData

class DifferUtil(private val oldList : List<TodoItemData>,
                 private val newList : List<TodoItemData>
                 ) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        compareTodoItems(oldList[oldItemPosition], newList[newItemPosition])

}

fun compareTodoItems(td1 : TodoItemData, td2 : TodoItemData) : Boolean {
    return (td1.id == td2.id &&
            td1.text == td2.text &&
            td1.importance == td2.importance &&
            td1.deadline == td2.deadline &&
            td1.createdAt == td2.createdAt &&
            td1.done == td2.done)
}