package com.example.todoapp

import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.todoapp.databinding.FragmentAddTaskBinding
import java.util.*

class AddTaskFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    private val calendar : Calendar = Calendar.getInstance()
    private val formatter = SimpleDateFormat("dd MMMM yyyy", Locale.US)
    private lateinit var binding: FragmentAddTaskBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menu = initializeMenu()
        binding.apply {
            btnDelete.setOnClickListener {
                findNavController().navigate(R.id.action_addTaskFragment_to_todoListFragment)
            }

            btnSave.setOnClickListener {
                val todoItem = TodoItem(
                    "aaa",
                    binding.etTodo.text.toString(),
                    Priority.MEDIUM,
                    false,
                    Date(10, 11, 12),
                    null,
                    null
                )
                MAIN.repo.addTodoItem(todoItem)
                findNavController().navigate(R.id.action_addTaskFragment_to_todoListFragment)
            }

            ibCancel.setOnClickListener {
                findNavController().navigate(R.id.action_addTaskFragment_to_todoListFragment)
            }

            llImportance.setOnClickListener {
                menu.show()
            }
        }
        menu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.item_high -> binding.tvCurrentPriority.text = getString(R.string.high_priority)
                R.id.item_medium -> binding.tvCurrentPriority.text =
                    getString(R.string.medium_priority)
                R.id.item_low -> binding.tvCurrentPriority.text = getString(R.string.low_priority)
            }
            false
        }
        binding.swDate.setOnCheckedChangeListener { _, _ ->
            if (binding.swDate.isChecked) {
                DatePickerDialog(this.requireContext(),
                    this,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
            else {
                binding.tvCurrentDate.text = "Не выбрано"
            }
        }
    }

    fun initializeMenu(): PopupMenu {
        val menu = PopupMenu(this.requireContext(), binding.llImportance)
        menu.menuInflater.inflate(R.menu.menu_priority, menu.menu)
        val item = menu.menu.getItem(0)
        val str = SpannableString(item.toString())
        str.setSpan(
            ForegroundColorSpan(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.color_light_red
                )
            ), 0, str.length, 0
        )
        item.setTitle(str)
        return menu
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        calendar.set(p1, p2, p3)
        val str : String = formatter.format(calendar.timeInMillis)
        binding.tvCurrentDate.text = str
    }
}