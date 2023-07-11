package com.example.todoapp.fragments.todolist

import com.example.todoapp.adapter.TodoAdapter
import com.example.todoapp.vm.TodoViewModel
import com.example.todoapp.vm.TodoViewModelFactory
import dagger.BindsInstance
import dagger.Subcomponent
import javax.inject.Scope

@Scope
annotation class TodoListScope

@Subcomponent
@TodoListScope
interface TodoListFragmentComponent {

    fun todoListFragmentViewComponent() : TodoListFragmentViewComponent.Factory
    fun viewModelFactory() :TodoViewModelFactory
    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance fragment: TodoListFragment,
            @BindsInstance viewModel: TodoViewModel
        ) : TodoListFragmentComponent
    }

    val adapter : TodoAdapter
}