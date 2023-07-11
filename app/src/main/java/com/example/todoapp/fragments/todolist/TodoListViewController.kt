package com.example.todoapp.fragments.todolist

import android.app.Activity
import android.util.Log
import android.view.MenuItem

import androidx.core.graphics.drawable.toDrawable
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.R
import com.example.todoapp.adapter.TodoAdapter
import com.example.todoapp.app.appComponent
import com.example.todoapp.databinding.FragmentTodoListBinding
import com.example.todoapp.vm.TodoViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import javax.inject.Inject

class TodoListViewController @Inject constructor(
    private val fragment: TodoListFragment,
    private val rootViewBinding : FragmentTodoListBinding,
    private val adapter : TodoAdapter,
    private val lifecycleOwner: LifecycleOwner,
    val viewModel : TodoViewModel
    ) {

    fun setUpViews() {
        setUpRcView()
        setUpToolbar()
        setUpBtnAdd()
    }

    private fun setUpRcView() = with (rootViewBinding){
        rvTodolist.adapter = adapter
        val layoutManager =
            LinearLayoutManager(fragment.requireActivity().applicationContext, LinearLayoutManager.VERTICAL, false)
        rvTodolist.layoutManager = layoutManager
        viewModel.listOfItems.observe(lifecycleOwner){
            newList ->
            adapter.setList(newList)
        }
    }

    private fun setUpToolbar() = rootViewBinding.toolbar.setOnMenuItemClickListener { item ->
        when (item.itemId) {
                R.id.menu_show_done -> {
                    changeVisibilityIcon(item, true)
                    true
                }

                R.id.menu_sync -> {
                    viewModel.syncro()
                    true
                }
                else -> false
            }
        }

    private fun setUpBtnAdd() = rootViewBinding.btnAdd.setOnClickListener{
        val directions =
            TodoListFragmentDirections.actionTodoListFragmentToAddTaskFragment(todoItemId = null)
        findNavController(rootViewBinding.root).navigate(directions)
    }

    //пока еще не успел сделать...
    private fun changeVisibilityIcon(item : MenuItem, isVisible : Boolean) {
        if (isVisible) item.icon = R.drawable.baseline_visibility_24.toDrawable()
        else item.icon = R.drawable.baseline_visibility_off_24.toDrawable()
    }
}