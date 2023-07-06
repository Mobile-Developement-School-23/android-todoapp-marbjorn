package com.example.todoapp.fragments.todolist

import com.example.todoapp.adapter.TodoAdapter
import com.example.todoapp.vm.TodoViewModel

class TodoListFragmentComponent(
    val fragment: TodoListFragment,
    val viewModel: TodoViewModel
) {
    val adapter = TodoAdapter(fragment, viewModel)
}