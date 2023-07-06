package com.example.todoapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.TaskItemBinding
import com.example.todoapp.fragments.todolist.TodoListFragment
import com.example.todoapp.fragments.todolist.TodoListFragmentDirections
import com.example.todoapp.storage.TodoItemData
import com.example.todoapp.vm.TodoViewModel

class TodoAdapter(val fragment: TodoListFragment, private val todoViewModel: TodoViewModel)
    : RecyclerView.Adapter<TodoViewHolder>() {
    private var todoItems = listOf<TodoItemData>()
    init{
        Log.d("Adapter", "Recreated")
    }

    fun setList(newList : List<TodoItemData>) {
        val (checked, unchecked) = newList.partition { todoItemData -> todoItemData.done }
        val sortedList = mutableListOf<TodoItemData>()
        sortedList.addAll(unchecked)
        sortedList.addAll(checked)
        val differUtil = DifferUtilCalculator(todoItems, sortedList)
        val diffResult = DiffUtil.calculateDiff(differUtil)
        todoItems = sortedList
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