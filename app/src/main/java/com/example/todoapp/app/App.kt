package com.example.todoapp.app

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.NonDisposableHandle.parent


@Module
class App : Application() {

    val appComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(this.applicationContext)
    }
    @Provides
    fun providesApplication() : Application {
        return this
    }

}

val Context.appComponent: ApplicationComponent
    get() = when(this) {
        is App -> appComponent
        else -> this.applicationContext.appComponent
    }
