package com.example.todoapp.fragments.addtask

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.Text
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.todoapp.databinding.FragmentAddTaskBinding
import com.example.todoapp.app.App
import com.example.todoapp.app.appComponent
import com.example.todoapp.vm.AddTaskModel
import com.example.todoapp.vm.AddTaskModelFactory
import javax.inject.Inject

class AddTaskFragment : Fragment() {

    private var _binding: FragmentAddTaskBinding? = null
    private val binding : FragmentAddTaskBinding
        get() = _binding!!

    var fragmentViewComponent : AddTaskFragmentViewComponent? = null

    private val args : AddTaskFragmentArgs by navArgs()

    private val viewModel: AddTaskModel by viewModels {
       requireContext().appComponent.addTaskFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        /*_binding = FragmentAddTaskBinding.inflate(inflater, container, false)*/
        /*fragmentViewComponent = requireContext().appComponent.addTaskFragmentViewComponent().create(
            this,
            args,
            binding,
            viewLifecycleOwner,
            viewModel
        ).apply {
            addTaskViewController.initLifeDataForChange()
        }*/
        return ComposeView(requireContext()).apply {
            setContent {
                AppTheme {
                    TodoTextFields()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentViewComponent = null
        _binding = null
    }
}


