package com.example.todoapp.data

import android.content.Context
import android.util.Log
import androidx.work.Configuration
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.todoapp.api.TodoApi
import com.example.todoapp.app.App
import com.example.todoapp.app.ApplicationComponent
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Provider


interface ChildWorkerFactory {
    fun create(appContext: Context, params: WorkerParameters): Worker
}

class NetworkWorker constructor(
    appContext: Context,
    workerParams: WorkerParameters
): CoroutineWorker(appContext, workerParams) {

    @Inject
    lateinit var repo : TodoRepository
    override suspend fun doWork(): Result {
        return try {
            repo.syncItemsFromRemote()
            Result.success()
        } catch (throwable: Throwable) {
            Log.e("Worker", throwable.toString())
            Result.failure()
        }
    }

    private fun injectDependencies() {
        val component = (applicationContext as App).appComponent
        component.workerComponent().create().inject(this)
    }
}