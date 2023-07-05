package com.example.todoapp

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.api.RetrofitInstance
import com.example.todoapp.api.TodoItemWrapper
import com.example.todoapp.databinding.ActivityMainBinding
import com.example.todoapp.storage.TodoItemData
import com.example.todoapp.vm.TodoViewModel
import com.google.gson.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var todoRecyclerView: RecyclerView
    private lateinit var binding : ActivityMainBinding
    lateinit var navController: NavController
    lateinit var todoViewModel : TodoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        todoViewModel = ViewModelProvider(this, defaultViewModelProviderFactory).get(TodoViewModel::class.java)
        val navFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navFragment.navController


/*
        CoroutineScope(Dispatchers.IO).launch {
            val todoList = RetrofitInstance.api.getListOfItems()
            Log.e("API", todoList.list.size.toString())
        }*/
/*
        val todo = TodoItemData(
            id = "6",
            text ="blabla",
            importance = "important",
            done = true,
            createdAt = System.currentTimeMillis()/1000,
            lastUpdatedBy = "1",
            changedAt = System.currentTimeMillis()/1000
        )

        var todoItemWrapper = TodoItemWrapper(
            status = "ok",
            element = todo
        )

        val gson = GsonBuilder().setPrettyPrinting().create()
        Log.e("API", gson.toJson(todoItemWrapper))


        CoroutineScope(Dispatchers.IO).launch {
            //val todoList = RetrofitInstance.api.addItem("5", todoItemResponseData)
            //val todoList = RetrofitInstance.api.patchListOfItems("6")
            //val todoList = RetrofitInstance.api.getItem("2")
            val todoList = RetrofitInstance.api.deleteItem("6","2")
            //val todoList = RetrofitInstance.api.getItem("1")
            Log.e("API", todoList.revision.toString())
        }*/
    }

}