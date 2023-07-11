package com.example.todoapp.fragments.todolist

import androidx.lifecycle.LifecycleOwner
import com.example.todoapp.databinding.FragmentTodoListBinding
import dagger.BindsInstance
import dagger.Subcomponent
import javax.inject.Scope

@Scope
annotation class TodoListViewScope

@TodoListViewScope
@Subcomponent
interface TodoListFragmentViewComponent {
    fun inject(todoViewController : TodoListViewController)
    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance binding: FragmentTodoListBinding,
            @BindsInstance lifecycleOwner: LifecycleOwner
        ) : TodoListFragmentViewComponent
    }

    val todoViewController : TodoListViewController
}