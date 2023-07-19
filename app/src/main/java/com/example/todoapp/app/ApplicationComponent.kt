package com.example.todoapp.app

import android.app.Application
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.NotificationCompat
import com.example.todoapp.data.SharedPrefs
import com.example.todoapp.api.NetworkConnectivityObserver
import com.example.todoapp.api.TodoApi
import com.example.todoapp.api.TodoRetrofit
import com.example.todoapp.data.NetworkWorkerComponent
import com.example.todoapp.data.TaskDao
import com.example.todoapp.data.TaskDatabase
import com.example.todoapp.data.TodoRepository
import com.example.todoapp.fragments.settingsdialog.SettingsDialogComponent
import com.example.todoapp.fragments.todolist.TodoListFragmentComponent
import com.example.todoapp.vm.AddTaskModelFactory
import com.example.todoapp.vm.TodoViewModelFactory
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Scope
import javax.inject.Singleton

@Scope
annotation class ApplicationScope

@Component(modules = [
    ConnectivityObserverModule::class,
    TodoRepositoryModule::class,
    RetrofitModule::class])
@ApplicationScope
interface ApplicationComponent {

    fun todoViewFactory() : TodoViewModelFactory
    fun addTaskFactory() : AddTaskModelFactory
    fun todoListFragmentComponent() : TodoListFragmentComponent.Factory
    fun workerComponent() : NetworkWorkerComponent.Factory
    fun settingsDialogComponent() : SettingsDialogComponent.Factory

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application,
            @BindsInstance context: Context): ApplicationComponent
    }
}

@Module
class ConnectivityObserverModule {
    @Provides
    fun providesConnectivityObserver(context : Context) : NetworkConnectivityObserver {
        return NetworkConnectivityObserver(context)
    }
}

@Module
class TodoRepositoryModule {
    @Provides
    fun providesTaskDatabase(context: Context) : TaskDatabase {
        return TaskDatabase.getDatabase(context)
    }
    @Provides
    fun providesTodoRepository(taskDao: TaskDao,
                               application: Application,
                               api : TodoApi,
                               prefs: SharedPrefs
    ) : TodoRepository {
        return TodoRepository(taskDao, prefs, api, application)
    }

    @Provides
    fun providesTaskDao(taskDatabase: TaskDatabase) : TaskDao {
        return taskDatabase.taskDao()
    }

    @Provides
    fun providePrefs(application: Application) : SharedPrefs {
        return SharedPrefs(application).apply {
            AppCompatDelegate.setDefaultNightMode(this.getSystemMode())
        }
    }


}

@Module
class RetrofitModule {
    @Provides
    fun getTodoApi() : TodoApi {
        return TodoRetrofit().api
    }
}