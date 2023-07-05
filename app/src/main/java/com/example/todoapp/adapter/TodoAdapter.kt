package com.example.todoapp.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.storage.Priority
import com.example.todoapp.R
import com.example.todoapp.databinding.TaskItemBinding
import com.example.todoapp.fragments.TodoListFragmentDirections
import com.example.todoapp.storage.TodoItemData
import com.example.todoapp.vm.TodoViewModel

class TodoAdapter(val fragmentView : View, val todoViewModel: TodoViewModel) : RecyclerView.Adapter<TodoViewHolder>() {


    var todoItems = listOf<TodoItemData>()

    fun setList(newList : List<TodoItemData>) {
        val differUtil = DifferUtil(todoItems, newList)
        val diffResult = DiffUtil.calculateDiff(differUtil)
        todoItems = newList
        diffResult.dispatchUpdatesTo(this)


    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {

        val _binding = TaskItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoViewHolder(_binding, todoViewModel)
    }

    override fun getItemCount() = todoItems.size

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.onBind(todoItems[position])
        holder.binding.ivInfo.setOnClickListener {
            val directions =
                TodoListFragmentDirections.actionTodoListFragmentToAddTaskFragment(todoItemId = todoItems[position].id)
            Navigation.findNavController(fragmentView).navigate(directions)
        }
    }
}