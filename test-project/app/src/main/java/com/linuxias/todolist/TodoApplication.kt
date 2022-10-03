package com.linuxias.todolist

import android.app.Application
import com.linuxias.todolist.db.TodoDatabase
import com.linuxias.todolist.db.TodoRepository

class TodoApplication : Application() {
    val database by lazy { TodoDatabase.getTodoDatabase(this) }
    val repository by lazy { TodoRepository(database!!.getTodoDao()) }
}