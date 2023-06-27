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
import com.example.todoapp.storage.Priority
import com.example.todoapp.R
import com.example.todoapp.storage.TodoItem
import com.example.todoapp.databinding.FragmentAddTaskBinding
import com.example.todoapp.repository.TodoRepository
import java.util.*
import kotlin.random.Random

class AddTaskFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    private val calendar : Calendar = Calendar.getInstance()
    private val formatter = SimpleDateFormat("dd MMMM yyyy", Locale("ru"))
    private lateinit var binding: FragmentAddTaskBinding
    private var currentTodoItem: TodoItem? = null
    private val args: AddTaskFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //initViewModel()
        val menu = initializeMenu()
        menu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.item_high -> {
                    setPriorityInView(Priority.HIGH)
                    currentTodoItem?.priority = Priority.HIGH
                }
                R.id.item_medium -> {
                    setPriorityInView(Priority.MEDIUM)
                    currentTodoItem?.priority = Priority.MEDIUM
                }
                R.id.item_low -> {
                    setPriorityInView(Priority.LOW)
                    currentTodoItem?.priority = Priority.LOW
                }
            }
            false
        }

        val idToModify : String? = args.todoItemId
        var todoItem : TodoItem? = idToModify?.let { TodoRepository.getTodoItem(it) }

        if (idToModify == null) {
            currentTodoItem = TodoItem(
                id = Random(100).nextInt().toString(),
                text = "",
                creationDate = calendar.time
            )
        }
        else {
            currentTodoItem = todoItem?.copy()
        }

        if (idToModify != null) currentTodoItem = TodoRepository.getTodoItem(idToModify)
        currentTodoItem?.let { initializeViews(it) }

        binding.apply {
            btnDelete.setOnClickListener {
                if (idToModify != null) {
                    TodoRepository.deleteTodoItem(idToModify)
                }
                findNavController().navigate(R.id.action_addTaskFragment_to_todoListFragment)
            }

            btnSave.setOnClickListener {
                currentTodoItem?.text = etTodo.text.toString()
                if (todoItem == null) {
                    currentTodoItem?.let { it1 -> TodoRepository.addTodoItem(it1) }
                } else if (!currentTodoItem?.let { it1 -> compareTodoItems(it1, todoItem!!) }!!) {
                    currentTodoItem?.changeDate = Calendar.getInstance().time
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
                    currentTodoItem?.deadlineDate = null
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

    fun initializeViews(todoItem: TodoItem) = with (binding) {
        etTodo.setText(todoItem.text)
        setPriorityInView(todoItem.priority)

        if (currentTodoItem?.deadlineDate == null ) swDate.isChecked = false
        else swDate.isChecked = true

        var str : String = "Не выбрано"
        if (currentTodoItem?.deadlineDate != null) {
            str = formatter.format(currentTodoItem?.deadlineDate?.time)
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
        currentTodoItem?.deadlineDate = calendar.time
    }

    fun compareTodoItems(td1 : TodoItem, td2 : TodoItem) : Boolean {
        return (td1.id == td2.id &&
                td1.text == td2.text &&
                td1.priority == td2.priority &&
                td1.deadlineDate == td2.deadlineDate &&
                td1.creationDate == td2.creationDate)
    }
}


