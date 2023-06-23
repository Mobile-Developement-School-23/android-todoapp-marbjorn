package com.example.todoapp

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.TaskItemBinding

class TodoAdapter(val fragmentView : View) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    class TodoViewHolder(val binding : TaskItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(todoItem : TodoItem) = with(binding){
            cbDone.setChecked(todoItem.isDone)
            tvText.text = todoItem.text
            strikeText(tvText)

            cbDone.setOnClickListener {
                todoItem.isDone = cbDone.isChecked
                strikeText(tvText)
            }
            when (todoItem.priority) {
                Priority.HIGH -> {
                    cbDone.setButtonDrawable(R.drawable.custom_check_box_high_prioroty_selector)
                    ivPriority.setImageResource(R.drawable.baseline_priority_high_24)
                }
                Priority.LOW -> {
                    ivPriority.setImageResource(R.drawable.baseline_arrow_downward_24)
                }
                else -> {
                    ivPriority.visibility = ImageView.GONE
                }
            }
        }

        fun strikeText(tv : TextView) {
            if (binding.cbDone.isChecked) {
                tv.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            }
            else {
                tv.paintFlags = 0
            }
        }
    }

    var todoItems = MAIN.repo.getTodoItems()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {

        val _binding = TaskItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoViewHolder(_binding)
    }

    override fun getItemCount() = todoItems.size

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.onBind(todoItems[position])
        holder.binding.ivInfo.setOnClickListener {
            val directions = TodoListFragmentDirections.actionTodoListFragmentToAddTaskFragment(todoItemId = todoItems[position].id)
            Navigation.findNavController(fragmentView).navigate(directions)
        }
    }
}