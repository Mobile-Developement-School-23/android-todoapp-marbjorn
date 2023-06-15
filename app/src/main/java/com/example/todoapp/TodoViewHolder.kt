package com.example.todoapp

import android.view.LayoutInflater
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.TaskItemBinding

class TodoViewHolder(todoView : View) : RecyclerView.ViewHolder(todoView) {

    private val done : CheckBox = todoView.findViewById(R.id.cb_done)
    private val text : TextView = todoView.findViewById(R.id.tv_text)
    private val info : ImageView = todoView.findViewById(R.id.iv_info)
    private val iconPriority : ImageView = todoView.findViewById(R.id.iv_priority)

    fun onBind(todoItem : TodoItem) {
        done.setChecked(todoItem.isDone)
        text.text = todoItem.text
        when (todoItem.priority) {
            Priority.HIGH -> {
                done.setButtonDrawable(R.drawable.custom_check_box_high_prioroty_selector)
                iconPriority.setImageResource(R.drawable.baseline_priority_high_24)
            }
            Priority.LOW -> {
                iconPriority.setImageResource(R.drawable.baseline_arrow_downward_24)
            }
            else -> {
                iconPriority.visibility = ImageView.GONE
            }
        }

        info.setOnClickListener {

        }
    }
}