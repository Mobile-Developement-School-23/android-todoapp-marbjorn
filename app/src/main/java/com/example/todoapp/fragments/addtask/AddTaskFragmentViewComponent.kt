package com.example.todoapp.fragments.addtask

import android.app.Activity
import androidx.lifecycle.LifecycleOwner
import com.example.todoapp.api.NetworkConnectivityObserver
import com.example.todoapp.data.TodoRepository
import com.example.todoapp.databinding.FragmentAddTaskBinding
import com.example.todoapp.vm.AddTaskModel
import com.example.todoapp.vm.AddTaskModelFactory
import dagger.Binds
import dagger.BindsInstance
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import javax.inject.Inject
import javax.inject.Scope

@Scope
annotation class AddTaskScope

@AddTaskScope
@Subcomponent
interface AddTaskFragmentViewComponent {
    fun inject(addTaskViewController: AddTaskViewController)
    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance fragment: AddTaskFragment,
            @BindsInstance args : AddTaskFragmentArgs,
            @BindsInstance binding : FragmentAddTaskBinding,
            @BindsInstance lifecycleOwner: LifecycleOwner,
            @BindsInstance viewModel: AddTaskModel
        ) : AddTaskFragmentViewComponent
    }

    var addTaskViewController : AddTaskViewController
}