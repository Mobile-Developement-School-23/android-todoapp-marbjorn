package com.example.todoapp.fragments.addtask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.todoapp.databinding.FragmentAddTaskBinding
import com.example.todoapp.fragments.App
import com.example.todoapp.vm.AddTaskModel

class AddTaskFragment : Fragment() {

    private var _binding: FragmentAddTaskBinding? = null
    private val binding : FragmentAddTaskBinding
        get() = _binding!!

    private val applicationComponent
        get() = App.get(requireContext()).applicationComponent

    private var fragmentViewComponent : AddTaskFragmentViewComponent? = null

    private val args : AddTaskFragmentArgs by navArgs()

    private val viewModel: AddTaskModel by viewModels{ applicationComponent.viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTaskBinding.inflate(inflater, container, false)

        fragmentViewComponent = AddTaskFragmentViewComponent(
            this,
            binding,
            args,
            viewModel,
            viewLifecycleOwner
        ).apply {
            addTaskViewController.initLifeDataForChange()
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentViewComponent = null
        _binding = null
    }
}


