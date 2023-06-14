package com.example.todoapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todoapp.databinding.FragmentAddTaskBinding
import com.example.todoapp.databinding.FragmentTodoListBinding
import java.util.*

class AddTaskFragment : Fragment() {

    private lateinit var binding : FragmentAddTaskBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnOk.setOnClickListener {
            val todoItem = TodoItem("aaa", binding.etTodo.text.toString(), Priority.MEDIUM, false, Date(10, 11, 12), null, null)
            MAIN.repo.addTodoItem(todoItem)
            MAIN.navController.navigate(R.id.action_addTaskFragment_to_todoListFragment)
        }
    }
}