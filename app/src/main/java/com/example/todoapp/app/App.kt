package com.example.todoapp.app

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.NonDisposableHandle.parent
import javax.inject.Inject


class App : Application() {

    val appComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(this, this.applicationContext)
    }

}

val Context.appComponent: ApplicationComponent
    get() = when(this) {
        is App -> appComponent
        else -> this.applicationContext.appComponent
    }