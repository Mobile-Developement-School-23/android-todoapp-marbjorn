package com.example.todoapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.FragmentTodoListBinding

class TodoListFragment : Fragment() {

    private lateinit var binding: FragmentTodoListBinding
    private lateinit var adapter: TodoAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTodoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAdd.setOnClickListener {
            MAIN.navController.navigate(R.id.action_todoListFragment_to_addTaskFragment)
        }
        adapter = TodoAdapter()
        recyclerView = binding.rvTodolist

        recyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false);
        recyclerView.layoutManager = layoutManager
        adapter.todoItems = MAIN.repo.getTodoItems(this.requireContext())
    }
}