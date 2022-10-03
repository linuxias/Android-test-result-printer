package com.linuxias.todolist.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(TodoEntity::class), version = 1)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun getTodoDao() : TodoDao

    companion object {
        val dbName = "Todo"

        @Volatile
        private var INSTANCE: TodoDatabase? = null

        fun getTodoDatabase(context : Context) : TodoDatabase? {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TodoDatabase::class.java,
                    dbName,
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}