package com.example.todoapp.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.get
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.MainActivity
import com.example.todoapp.R
import com.example.todoapp.fragments.TodoListFragmentDirections
import com.example.todoapp.adapter.TodoAdapter
import com.example.todoapp.databinding.FragmentTodoListBinding
import com.example.todoapp.vm.TodoViewModel
import com.google.android.material.snackbar.Snackbar

class TodoListFragment : Fragment() {
    private lateinit var binding: FragmentTodoListBinding
    private lateinit var adapter: TodoAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel : TodoViewModel
    private var isDoneShown = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTodoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).todoViewModel
        binding.btnAdd.setOnClickListener {
            val directions =
                TodoListFragmentDirections.actionTodoListFragmentToAddTaskFragment(todoItemId = null)
            findNavController().navigate(directions)
        }

        initRcView(view)

        viewModel.tasks.observe(viewLifecycleOwner)
        { newList ->
            adapter.setList(newList.filter { isDoneShown||it.done })
        }

        binding.btnRefresh.setOnClickListener{
            viewModel.updateDataFromServer()
        }

        //val item : MenuItem = binding.toolbar.menu.findItem(R.id.menu_show_done)


        //пробую обновить
        //viewModel.updateDataFromServer()
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val inflater: MenuInflater = inflater
        inflater.inflate(R.menu.menu_todolist, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.e("Click", "Event")
        // Switching on the item id of the menu item
        return when (item.itemId) {
            R.id.menu_show_done -> {
                // Code to be executed when the add button is clicked
                Toast.makeText(requireContext(), "Menu Item is Pressed", Toast.LENGTH_SHORT).show()
                if (isDoneShown) {
                    item.icon = ContextCompat.getDrawable(requireContext(), R.drawable.baseline_visibility_24)
                }
                else {
                    item.icon = ContextCompat.getDrawable(requireContext(), R.drawable.baseline_visibility_off_24)
                }
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun initRcView(view : View) {
        adapter = TodoAdapter(view, viewModel)
        recyclerView = binding.rvTodolist
        recyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false);
        recyclerView.layoutManager = layoutManager
    }
/*
    var changeVisibleButton = with(binding){
        val isShown = binding.toolbar.menu.get(R.id.menu_show_done)
        if (isDoneShown) {
            isShown.icon = ContextCompat.getDrawable(requireContext(), R.drawable.baseline_visibility_24)
        }
        else {
            isShown.icon = ContextCompat.getDrawable(requireContext(), R.drawable.baseline_visibility_off_24)
        }
    }*/
}
