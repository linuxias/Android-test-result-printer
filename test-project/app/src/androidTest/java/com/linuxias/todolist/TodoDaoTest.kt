package com.linuxias.todolist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.linuxias.todolist.db.TodoDao
import com.linuxias.todolist.db.TodoDatabase
import com.linuxias.todolist.db.TodoEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class TodoDaoTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: TodoDatabase
    private lateinit var dao: TodoDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TodoDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.getTodoDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertTodoTest() = runBlockingTest {
        val todo = TodoEntity(
            id = 1,
            title = "Title",
            importance = 1
        )

        dao.insertTodo(todo)

        val allTodoEntities = dao.getAll().first()
        assertThat(allTodoEntities).doesNotContain(todo)
    }

    @Test
    fun deleteTodoTest() = runBlockingTest {
        val todo = TodoEntity(
            id = 1,
            title = "Title",
            importance = 1
        )

        dao.insertTodo(todo)
        dao.deleteTodo(todo)

        val allTodoEntities = dao.getAll().first()
        assertThat(allTodoEntities).doesNotContain(todo)
    }
}