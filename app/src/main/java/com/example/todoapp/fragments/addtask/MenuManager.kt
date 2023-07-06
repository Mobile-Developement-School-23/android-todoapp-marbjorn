package com.example.todoapp.fragments.addtask

import android.content.Context
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.todoapp.R
import com.example.todoapp.storage.Priority

class MenuManager(private val context : Context,
                  initialPriority: Priority,
                  view : View,
                  private val optionsList : Int,
                  private val importanceColor : Int) {

    private val menu : PopupMenu

    private var _priority = MutableLiveData(initialPriority)
    val priority : LiveData<Priority>

    init {
        priority = _priority
        menu = PopupMenu(context, view)
        view.setOnClickListener {
            menu.show()
        }
        setMenu()
        setUpMenuListener()
    }

    private fun setMenu() {
        Log.d("Menu", "Constuctor")
        menu.menuInflater.inflate(optionsList, menu.menu)
        val item = menu.menu.getItem(0)
        val str = SpannableString(item.toString())
        val highImportance = ContextCompat.getColor(
            context,
            importanceColor
        )
        str.setSpan(
            ForegroundColorSpan(
                highImportance
            ), 0, str.length, 0
        )
        item.title = str
    }



    private fun setUpMenuListener() = menu.setOnMenuItemClickListener {
        Log.d("Menu", "Clicked")
        when (it.itemId) {
            R.id.item_high -> {
                _priority.value = Priority.HIGH
                true
            }

            R.id.item_medium -> {
                _priority.value = Priority.MEDIUM
                true
            }

            R.id.item_low -> {
                _priority.value = Priority.LOW
                true
            }
            else -> false
        }

    }

    fun setPriority(priority: Priority) {
        _priority.value = priority
    }

}