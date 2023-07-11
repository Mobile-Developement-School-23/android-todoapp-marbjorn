package com.example.todoapp.app

import android.app.Application
import android.content.Context
import com.example.todoapp.data.SharedPrefs
import com.example.todoapp.api.NetworkConnectivityObserver
import com.example.todoapp.api.TodoApi
import com.example.todoapp.api.TodoRetrofit
import com.example.todoapp.data.NetworkWorker
import com.example.todoapp.data.NetworkWorkerComponent
import com.example.todoapp.data.PeriodicWorkRequestModule
import com.example.todoapp.data.TaskDao
import com.example.todoapp.data.TaskDatabase
import com.example.todoapp.data.TodoRepository
import com.example.todoapp.data.WorkManagerModule
import com.example.todoapp.fragments.addtask.AddTaskFragmentViewComponent
import com.example.todoapp.fragments.todolist.TodoListFragmentComponent
import com.example.todoapp.vm.AddTaskModelFactory
import com.example.todoapp.vm.TodoViewModelFactory
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Scope

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
    fun addTaskFragmentViewComponent() : AddTaskFragmentViewComponent.Factory

    fun workerComponent() : NetworkWorkerComponent.Factory

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
                               context: Context,
                               api : TodoApi,
                               prefs: SharedPrefs
    ) : TodoRepository {
        return TodoRepository(taskDao, prefs, api, context)
    }

    @Provides
    fun providesTaskDao(taskDatabase: TaskDatabase) : TaskDao {
        return taskDatabase.taskDao()
    }

    @Provides
    fun providePrefs(context: Context) : SharedPrefs {
        return SharedPrefs(context)
    }

}

@Module
class RetrofitModule {
    @Provides
    fun getTodoApi() : TodoApi {
        return TodoRetrofit().api
    }
}