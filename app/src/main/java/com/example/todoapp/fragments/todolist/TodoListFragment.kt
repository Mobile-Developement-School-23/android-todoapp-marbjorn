package com.example.todoapp.fragments.todolist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.todoapp.databinding.FragmentTodoListBinding
import com.example.todoapp.app.appComponent
import com.example.todoapp.vm.TodoViewModel

class TodoListFragment : Fragment() {
    private var _binding: FragmentTodoListBinding? = null

    private val binding : FragmentTodoListBinding
        get() = _binding!!

    private lateinit var fragmentComponent : TodoListFragmentComponent


    private val viewModel: TodoViewModel by viewModels {
        requireContext().appComponent.todoViewFactory()
    }


    private var fragmentViewComponent : TodoListFragmentViewComponent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentComponent = requireContext().appComponent
            .todoListFragmentComponent().create(this)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoListBinding.inflate(inflater, container, false)

        fragmentViewComponent = fragmentComponent
            .todoListFragmentViewComponent()
            .create(binding, this).apply {
                todoViewController.setUpViews()
            }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentViewComponent = null
        _binding = null
    }
}

