package com.example.todoapp.fragments.todolist

import androidx.lifecycle.LifecycleOwner
import com.example.todoapp.databinding.FragmentTodoListBinding

class TodoListFragmentViewComponent(
    fragmentComponent: TodoListFragmentComponent,
    binding: FragmentTodoListBinding,
    lifecycleOwner: LifecycleOwner,
) {

    val todoListViewController = TodoListViewController(
        fragmentComponent.fragment.requireActivity(),
        binding,
        fragmentComponent.adapter,
        lifecycleOwner,
        fragmentComponent.viewModel,
    )
}