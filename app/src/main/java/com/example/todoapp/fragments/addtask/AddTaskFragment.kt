package com.example.todoapp.fragments.addtask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todoapp.app.appComponent
import com.example.todoapp.vm.AddTaskModel

import com.google.accompanist.themeadapter.appcompat.AppCompatTheme
import com.google.accompanist.themeadapter.appcompat.createAppCompatTheme
import com.google.accompanist.themeadapter.material.MdcTheme
import com.google.accompanist.themeadapter.material.createMdcTheme
import com.google.accompanist.themeadapter.material3.Mdc3Theme
import com.google.accompanist.themeadapter.material3.createMdc3Theme

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
                    AddTaskScreen(
                        viewModel.stateTodoItem,
                        viewModel.stateInit,
                        viewModel::onAddTaskEvent,
                        findNavController()
                    )
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


