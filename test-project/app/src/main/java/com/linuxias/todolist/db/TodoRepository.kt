package com.linuxias.todolist.db

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

class TodoRepository(private val todoDao: TodoDao) {
    val allTodoList: Flow<List<TodoEntity>> = todoDao.getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(todo: TodoEntity) {
        todoDao.insertTodo(todo)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(todo: TodoEntity) {
        todoDao.deleteTodo(todo)
    }
}