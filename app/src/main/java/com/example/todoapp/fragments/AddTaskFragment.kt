package com.example.todoapp.fragments

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
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todoapp.MainActivity
import com.example.todoapp.storage.Priority
import com.example.todoapp.R
import com.example.todoapp.adapter.compareTodoItems
import com.example.todoapp.databinding.FragmentAddTaskBinding
import com.example.todoapp.storage.TodoItemData
import com.example.todoapp.vm.TodoViewModel
import java.util.*
import kotlin.random.Random

class AddTaskFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    private val calendar : Calendar = Calendar.getInstance()
    private val formatter = SimpleDateFormat("dd MMMM yyyy", Locale("ru"))
    private lateinit var binding: FragmentAddTaskBinding
    private var currentTodoItem: TodoItemData? = null
    private val args: AddTaskFragmentArgs by navArgs()
    lateinit var viewModel : TodoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).todoViewModel
        val menu = initializeMenu()
        menu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.item_high -> {
                    setPriorityInView(Priority.HIGH)
                    currentTodoItem?.importance = Priority.HIGH
                }
                R.id.item_medium -> {
                    setPriorityInView(Priority.MEDIUM)
                    currentTodoItem?.importance = Priority.MEDIUM
                }
                R.id.item_low -> {
                    setPriorityInView(Priority.LOW)
                    currentTodoItem?.importance = Priority.LOW
                }
            }
            false
        }

        val idToModify : String? = args.todoItemId
        var todoItem : TodoItemData? = idToModify?.let { viewModel.getTodoItemById(idToModify) }

        if (idToModify == null) {
            currentTodoItem = TodoItemData(
                id = Random(100).nextInt().toString(),
                text = "",
                createdAt = calendar.timeInMillis/1000,
                changedAt = calendar.timeInMillis/1000,
                lastUpdatedBy = "1"
            )
        }
        else {
            currentTodoItem = todoItem?.copy()
        }

        if (idToModify != null) currentTodoItem = viewModel.getTodoItemById(idToModify)
        currentTodoItem?.let { initializeViews(it) }

        binding.apply {
            btnDelete.setOnClickListener {
                if (idToModify != null) {
                   viewModel.delete(viewModel.getTodoItemById(idToModify)!!)
                }
                findNavController().navigate(R.id.action_addTaskFragment_to_todoListFragment)
            }

            btnSave.setOnClickListener {
                currentTodoItem?.text = etTodo.text.toString()
                if (todoItem == null) {
                    currentTodoItem?.let {
                            it1 -> it1.id = UUID.randomUUID().toString()
                            viewModel.add(it1)
                    }
                } else if (!currentTodoItem?.let { it1 -> compareTodoItems(it1, todoItem!!) }!!) {
                    currentTodoItem?.changedAt = Calendar.getInstance().timeInMillis
                    todoItem = currentTodoItem?.copy()
                }
                findNavController().navigate(R.id.action_addTaskFragment_to_todoListFragment)
            }

            ibCancel.setOnClickListener {
                findNavController().navigate(R.id.action_addTaskFragment_to_todoListFragment)
            }

            llImportance.setOnClickListener {
                menu.show()
            }

        }

            binding.swDate.setOnClickListener {
                if (binding.swDate.isChecked) {
                    DatePickerDialog(
                        this.requireContext(),
                        this,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    ).show()

                } else {
                    currentTodoItem?.deadline = null
                    binding.tvCurrentDate.text = "Не выбрано"
                }
            }
    }

    fun setPriorityInView(priority: Priority) = with(binding) {
        when (priority) {
            Priority.HIGH -> tvCurrentPriority.text = getString(R.string.priority_high)
            Priority.MEDIUM -> tvCurrentPriority.text = getString(R.string.priority_common)
            Priority.LOW -> tvCurrentPriority.text = getString(R.string.priority_low)
        }
    }

    fun initializeViews(todoItem: TodoItemData) = with (binding) {
        etTodo.setText(todoItem.text)
        setPriorityInView(todoItem.importance)

        if (currentTodoItem?.deadline == null ) swDate.isChecked = false
        else swDate.isChecked = true

        var str : String = "Не выбрано"
        if (currentTodoItem?.deadline != null) {
            str = formatter.format(currentTodoItem?.deadline)
        }
        tvCurrentDate.text = str
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
        currentTodoItem?.deadline = calendar.timeInMillis
    }
}


