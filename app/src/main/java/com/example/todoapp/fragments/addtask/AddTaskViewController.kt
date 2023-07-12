package com.example.todoapp.fragments.addtask

import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation.findNavController
import com.example.todoapp.R
import com.example.todoapp.adapter.areTodoItemsEqual
import com.example.todoapp.databinding.FragmentAddTaskBinding
import com.example.todoapp.model.Priority
import com.example.todoapp.model.TodoItemData
import com.example.todoapp.vm.AddTaskModel
import java.util.Calendar
import java.util.Locale
import java.util.UUID
import javax.inject.Inject

class AddTaskViewController @Inject constructor(
    val fragment: AddTaskFragment,
    val binding: FragmentAddTaskBinding,
    val args: AddTaskFragmentArgs,
    val viewModel: AddTaskModel,
    val lifecycleOwner: LifecycleOwner,
) {
    private var todoItemData: TodoItemData? = null
    init {/*
        Log.d("viewModelState", viewModel.priority.value.toString())
        Log.d("viewModelState", viewModel.deadlineDate.value.toString())
        Log.d("viewModelState", viewModel.hasDeadline.value.toString())*/
    }
/*


    private fun setUpViews() = with(binding){
        tvCurrentDate.text = formatDate(viewModel.deadlineDate.value)
        Log.d("deadline", viewModel.deadlineDate.value.toString())
        swDate.isChecked = viewModel.hasDeadline.value!!
        Log.d("viewModelStateView", viewModel.priority.value.toString())
        setUpMenu()
        setUpSwitch()
        setUpButtons()
        setUpDate()
    }
    private fun setUpMenu() {
        val menuManager = MenuManager(fragment.requireActivity().applicationContext,
            viewModel.priority.value!!,
            binding.llImportance,
            R.menu.menu_priority,
            R.color.color_light_red
        )
        menuManager.setPriority(viewModel.priority.value!!)
        Log.d("MENU", viewModel.priority.value.toString())
        viewModel.priority = menuManager.priority as MutableLiveData<Priority>
        viewModel.priority.observe(lifecycleOwner) {
            //setPriorityInView(viewModel.priority.value!!)
        }
    }



    private fun setUpSwitch() = binding.swDate.setOnClickListener{
        viewModel.hasDeadline.value = binding.swDate.isChecked
    }

    private fun setUpButtons() = with(binding){
        btnSave.setOnClickListener {
            val todoItem = buildTodoItem(todoItemData)
            if (todoItemData == null) {
                Log.d("AddTask", "toSave")
                Log.d("ItemToSave", todoItem.toString())
                //viewModel.add(todoItem)
            }
            else if (!areTodoItemsEqual(todoItem, todoItemData!!)) {
                Log.d("AddTask", "toSave")
                Log.d("ItemToSave", todoItem.toString())
                todoItem.changedAt = Calendar.getInstance().timeInMillis/1000
                //viewModel.change(todoItem)
            }
            findNavController(binding.root).navigate(R.id.action_addTaskFragment_to_todoListFragment)
        }

        btnDelete.setOnClickListener {
            if (todoItemData != null) {
                Log.d("AddTask", "toDelete")
                viewModel.delete(todoItemData!!)
            }
            findNavController(binding.root).navigate(R.id.action_addTaskFragment_to_todoListFragment)
        }

        ibCancel.setOnClickListener {
            findNavController(binding.root).navigate(R.id.action_addTaskFragment_to_todoListFragment)
        }
    }

    private fun buildTodoItem(reference : TodoItemData?) : TodoItemData = with(binding){
        val calendar = Calendar.getInstance()
        val newId = reference?.id ?: UUID.randomUUID().toString()
        val newCreatedAt = reference?.createdAt ?: calendar.timeInMillis/1000
        return TodoItemData(
            id = newId,
            text = etTodo.text.toString(),
            deadline = viewModel.deadlineDate.value,
            importance = viewModel.priority.value!!,
            lastUpdatedBy = "1",
            createdAt = newCreatedAt,
            changedAt = calendar.timeInMillis/1000
        )
    }

    private fun setUpDate() = with(binding){
        val dateManager = DatePickerManager(fragment.requireActivity(), viewModel.deadlineDate.value)

        viewModel.deadlineDate = dateManager.date
        viewModel.deadlineDate.observe(lifecycleOwner) {
            tvCurrentDate.text = formatDate(viewModel.deadlineDate.value)
        }

        swDate.setOnClickListener{
            viewModel.hasDeadline.value = binding.swDate.isChecked
            if (binding.swDate.isChecked) {
                dateManager.showDatePickerDialog().show()
                Log.d("Date", viewModel.deadlineDate.value.toString())
            }
            else {
                dateManager.date.value = null
            }
        }
    }

    private fun formatDate(date : Long?, defaultValue : String = "Не выбрано") : String {
        val formatter = SimpleDateFormat("dd MMMM yyyy", Locale("ru"))
        return if (date != null) formatter.format(date)
        else defaultValue
    }*/
}