package com.example.todoapp

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TodoViewHolder(todoView : View) : RecyclerView.ViewHolder(todoView) {
    private val done : CheckBox = todoView.findViewById(R.id.cb_done)
    private val text : TextView = todoView.findViewById(R.id.tv_text)
    fun onBind(todoItem : TodoItem) {
        done.setChecked(todoItem.isDone)
        text.text = todoItem.text
    }
}