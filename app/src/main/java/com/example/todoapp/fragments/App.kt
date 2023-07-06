package com.example.todoapp.fragments

import android.app.Application
import android.content.Context

class App : Application() {

    val applicationComponent by lazy { ApplicationComponent(this) }

    companion object {
        fun get(context: Context): App = context.applicationContext as App
    }
}
