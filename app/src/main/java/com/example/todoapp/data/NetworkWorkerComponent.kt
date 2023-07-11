package com.example.todoapp.data

import android.app.Application
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.todoapp.app.ApplicationScope
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import java.util.concurrent.TimeUnit
import javax.inject.Scope

@Scope
annotation class WorkerScope

@WorkerScope
@Subcomponent (modules = [PeriodicWorkRequestModule::class,
    WorkManagerModule::class])
interface NetworkWorkerComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): NetworkWorkerComponent
    }

    fun inject(work: NetworkWorker)
}

@Module
class WorkManagerModule {
    @Provides
    fun provideWorkManager(application: Application): WorkManager {
        return WorkManager.getInstance(application)
    }
}

@Module
class PeriodicWorkRequestModule {
    @Provides
    fun provideWorkRequest(): PeriodicWorkRequest {
        return PeriodicWorkRequestBuilder<NetworkWorker>(
            8,
            TimeUnit.HOURS
        )
            .build()
    }
}