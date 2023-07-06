package com.example.todoapp.fragments.addtask

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.example.todoapp.databinding.FragmentAddTaskBinding
import com.example.todoapp.vm.AddTaskModel

class AddTaskFragmentViewComponent (
    fragment: Fragment,
    binding: FragmentAddTaskBinding,
    args: AddTaskFragmentArgs,
    viewModel: AddTaskModel,
    lifecycleOwner: LifecycleOwner,
) {

    val addTaskViewController = AddTaskViewController(
        fragment.requireActivity(),
        binding,
        args,
        viewModel,
        lifecycleOwner
    )
}