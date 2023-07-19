package com.example.todoapp.adapter

import android.animation.ValueAnimator
import android.graphics.Paint
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.databinding.TaskItemBinding
import com.example.todoapp.model.Priority
import com.example.todoapp.model.TodoItemData
import com.example.todoapp.vm.TodoViewModel
import java.util.Calendar

class TodoViewHolder(
    val binding : TaskItemBinding,
    private val viewModel: TodoViewModel
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var animator : ValueAnimator
        fun onBind(todoItem : TodoItemData) = with(binding){
            cbDone.isChecked = todoItem.done
            setAnimation()
            tvText.text = todoItem.text
            strikeText(tvText)
            setIconVisibility(todoItem)
            modifyByImportance(todoItem)
            cbDone.setOnClickListener {
                animator.start()
                todoItem.done = cbDone.isChecked
                setIconVisibility(todoItem)
                todoItem.changedAt = Calendar.getInstance().timeInMillis
                strikeText(tvText)
                modifyByImportance(todoItem)
                viewModel.change(todoItem)
            }

        }

        private fun strikeText(tv : TextView) {
            if (binding.cbDone.isChecked) tv.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            else tv.paintFlags = 0
        }

        private fun modifyByImportance(todoItem: TodoItemData) = with (binding) {
            when (todoItem.importance) {
                Priority.HIGH -> {
                    cbDone.setButtonDrawable(R.drawable.custom_check_box_high_prioroty_selector)
                    ivPriority.setImageResource(R.drawable.baseline_priority_high_24)
                    }

                Priority.LOW -> {
                    cbDone.setButtonDrawable(R.drawable.custom_check_box_selector)
                    ivPriority.setImageResource(R.drawable.baseline_arrow_downward_24)
                    }
                Priority.MEDIUM -> {
                    cbDone.setButtonDrawable(R.drawable.custom_check_box_selector)
                    ivPriority.visibility = ImageView.GONE
                }
            }
        }


    private fun setIconVisibility(todoItem: TodoItemData) = with(binding) {
        if (!todoItem.done) ivPriority.visibility = ImageView.VISIBLE
        else ivPriority.visibility = ImageView.GONE
    }

    private fun setAnimation() = with(binding) {
        animator = ValueAnimator.ofFloat(0f, 1f)
        animator.duration = 750
        animator.interpolator = LinearInterpolator()
        animator.addUpdateListener { anim ->
            anim.animatedValue as Float
            cbDone.alpha = anim.animatedFraction
        }
    }
    }