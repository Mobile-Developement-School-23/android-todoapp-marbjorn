package com.example.todoapp.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.fragments.TodoListFragmentDirections
import com.example.todoapp.adapter.TodoAdapter
import com.example.todoapp.databinding.FragmentTodoListBinding
import com.google.android.material.snackbar.Snackbar

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
        //requestPermission()
        binding.btnAdd.setOnClickListener {
            val directions =
                TodoListFragmentDirections.actionTodoListFragmentToAddTaskFragment(todoItemId = null)
            findNavController().navigate(directions)
        }
        adapter = TodoAdapter(view)
        recyclerView = binding.rvTodolist
        recyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false);
        recyclerView.layoutManager = layoutManager
    }

    private fun requestPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.INTERNET
            ) == PackageManager.PERMISSION_GRANTED -> {
                Log.i("PERM", "granted")
            }
            ActivityCompat.shouldShowRequestPermissionRationale(
                this.requireActivity(),
                Manifest.permission.INTERNET
            ) -> {
                val bar = Snackbar.make(
                    binding.root,
                    "Permission Required",
                    Snackbar.LENGTH_INDEFINITE)
                bar.setAction("Ok") {
                    requestPermissionListener.launch(
                        Manifest.permission.INTERNET
                    )
                }
                bar.show()
            }
            else -> {
                requestPermissionListener.launch(
                    Manifest.permission.INTERNET
                )
            }
        }
    }

    private val requestPermissionListener = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
            isGranted : Boolean ->
        if (isGranted) {
            Log.i("Permission: ", "Granted")
        }
        else {
            Log.i("Permission: ", "Not granted")
        }
    }
}

