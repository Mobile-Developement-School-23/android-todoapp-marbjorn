package com.example.todoapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView

class TodoAdapter(fragmentView : View) : RecyclerView.Adapter<TodoViewHolder>() {

    private var view: View = fragmentView
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
        holder.info.setOnClickListener {
            val directions = TodoListFragmentDirections.actionTodoListFragmentToAddTaskFragment(todoItemId = todoItems[position].id)
            Navigation.findNavController(view).navigate(directions)
        }
    }
}