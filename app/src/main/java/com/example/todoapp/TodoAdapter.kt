package com.example.todoapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TodoAdapter : RecyclerView.Adapter<TodoViewHolder>() {

    var todoItems = listOf<TodoItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val inflater = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.task_item,
                parent,
                false)
        return TodoViewHolder(inflater)
    }

    override fun getItemCount() = todoItems.size

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.onBind(todoItems[position])
    }
}