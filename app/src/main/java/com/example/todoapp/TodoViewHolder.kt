package com.example.todoapp

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.TaskItemBinding
import java.text.SimpleDateFormat

class TodoViewHolder(todoView: View) : RecyclerView.ViewHolder(todoView) {

    val done : CheckBox = todoView.findViewById(R.id.cb_done)
    val text : TextView = todoView.findViewById(R.id.tv_text)
    val info : ImageView = todoView.findViewById(R.id.iv_info)
    val iconPriority : ImageView = todoView.findViewById(R.id.iv_priority)
    fun onBind(todoItem : TodoItem) {
        done.setChecked(todoItem.isDone)
        text.text = todoItem.text
        strikeText(text)

        done.setOnClickListener {
            todoItem.isDone = done.isChecked
            strikeText(text)
        }
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
    }

    fun strikeText(tv : TextView) {
        if (done.isChecked) {
            tv.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        }
        else {
            tv.paintFlags = 0
        }
    }
}