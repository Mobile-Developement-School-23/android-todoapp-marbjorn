package com.example.todoapp.fragments.addtask

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todoapp.databinding.FragmentAddTaskBinding
import com.example.todoapp.app.App
import com.example.todoapp.app.appComponent
import com.example.todoapp.vm.AddTaskModel
import com.example.todoapp.vm.AddTaskModelFactory
import javax.inject.Inject

class AddTaskFragment : Fragment() {

    var fragmentViewComponent : AddTaskFragmentViewComponent? = null

    private val args : AddTaskFragmentArgs by navArgs()

    private val viewModel: AddTaskModel by viewModels {
       requireContext().appComponent.addTaskFactory()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel.initLifeDataForChange(args.todoItemId)
        return ComposeView(requireContext()).apply {
            setContent {
                AppTheme {
                    AddTaskScreen(viewModel = viewModel, navController = findNavController())
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentViewComponent = null
    }
}


