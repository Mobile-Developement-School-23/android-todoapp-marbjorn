package com.example.todoapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.TaskItemBinding
import com.example.todoapp.fragments.todolist.TodoListFragment
import com.example.todoapp.fragments.todolist.TodoListFragmentDirections
import com.example.todoapp.model.TodoItemData
import com.example.todoapp.vm.TodoViewModel
import javax.inject.Inject

class TodoAdapter @Inject constructor(val fragment: TodoListFragment, val todoViewModel : TodoViewModel)
    : RecyclerView.Adapter<TodoViewHolder>() {
    private var todoItems = listOf<TodoItemData>()

    //
    fun setList(newList : List<TodoItemData>) {/*
        val (checked, unchecked) = newList.partition { todoItemData -> todoItemData.done }
        val sortedList = mutableListOf<TodoItemData>()
        sortedList.addAll(unchecked)
        sortedList.addAll(checked)
        val differUtil = DifferUtilCalculator(todoItems, sortedList)
        val diffResult = DiffUtil.calculateDiff(differUtil)
        todoItems = sortedList
        */

        val differUtil = DifferUtilCalculator(todoItems, newList)
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
            fragment.findNavController().navigate(directions)
        }
    }
}