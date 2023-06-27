package com.example.todoapp

import android.Manifest
import android.Manifest.permission.ACCESS_NETWORK_STATE
import android.Manifest.permission.INTERNET
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.api.RetrofitInstance
import com.example.todoapp.api.TodoApi
import com.example.todoapp.api.TodoItemData
import com.example.todoapp.api.TodoItemResponseData
import com.example.todoapp.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.gson.*
import com.google.gson.internal.GsonBuildConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    lateinit var todoRecyclerView: RecyclerView
    private lateinit var binding : ActivityMainBinding
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val navFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navFragment.navController


/*
        CoroutineScope(Dispatchers.IO).launch {
            val todoList = RetrofitInstance.api.getListOfItems()
            Log.e("API", todoList.list.size.toString())
        }*/

        val todo = TodoItemData(
            id = "6",
            text ="blabla",
            importance = "important",
            done = true,
            createdAt = System.currentTimeMillis()/1000,
            lastUpdatedBy = "1",
            changedAt = System.currentTimeMillis()/1000
        )

        var todoItemResponseData = TodoItemResponseData(
            status = "ok",
            element = todo
        )

        val gson = GsonBuilder().setPrettyPrinting().create()
        Log.e("API", gson.toJson(todoItemResponseData))


        CoroutineScope(Dispatchers.IO).launch {
            //val todoList = RetrofitInstance.api.addItem("5", todoItemResponseData)
            //val todoList = RetrofitInstance.api.patchListOfItems("6")
            //val todoList = RetrofitInstance.api.getItem("2")
            val todoList = RetrofitInstance.api.deleteItem("6","2")
            //val todoList = RetrofitInstance.api.getItem("1")
            Log.e("API", todoList.revision.toString())
        }
    }

}