package com.example.todoapp.fragments.todolist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.todoapp.databinding.FragmentTodoListBinding
import com.example.todoapp.fragments.App
import com.example.todoapp.vm.TodoViewModel

class TodoListFragment : Fragment() {
    private var _binding: FragmentTodoListBinding? = null
    private val binding : FragmentTodoListBinding
        get() = _binding!!


    private val applicationComponent
        get() = App.get(requireContext()).applicationComponent
    private lateinit var fragmentComponent: TodoListFragmentComponent
    private var fragmentViewComponent : TodoListFragmentViewComponent? = null
    private val viewModel: TodoViewModel by viewModels{ applicationComponent.viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentComponent = TodoListFragmentComponent(
            fragment = this,
            viewModel = viewModel
        )
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoListBinding.inflate(inflater, container, false)
        fragmentViewComponent = TodoListFragmentViewComponent(
            fragmentComponent,
            binding,
            viewLifecycleOwner
        ).apply {
            todoListViewController.setUpViews()
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentViewComponent = null
        _binding = null
    }
}

