package com.example.todoapp.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.todoapp.storage.TodoItemData

@Database(entities = arrayOf(TodoItemData::class), version = 1, exportSchema = false)
@TypeConverters(PriorityConverter::class)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile
        private var INSTANCE: TaskDatabase? = null

        fun getDatabase(context: Context): TaskDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TaskDatabase::class.java,
                        "task_database"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }


}