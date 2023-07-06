package com.example.todoapp.adapter

import android.graphics.Paint
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.databinding.TaskItemBinding
import com.example.todoapp.storage.Priority
import com.example.todoapp.storage.TodoItemData
import com.example.todoapp.vm.TodoViewModel
import java.util.Calendar

class TodoViewHolder(
    val binding : TaskItemBinding,
    private val viewModel: TodoViewModel
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(todoItem : TodoItemData) = with(binding){
            cbDone.isChecked = todoItem.done
            tvText.text = todoItem.text
            strikeText(tvText)

            cbDone.setOnClickListener {
                todoItem.done = cbDone.isChecked
                todoItem.changedAt = Calendar.getInstance().timeInMillis
                strikeText(tvText)
                viewModel.change(todoItem)
            }
            when (todoItem.importance) {
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

        private fun strikeText(tv : TextView) {
            if (binding.cbDone.isChecked) tv.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            else tv.paintFlags = 0
        }
    }