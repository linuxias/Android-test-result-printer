package com.linuxias.todolist

import androidx.lifecycle.*
import com.linuxias.todolist.db.TodoEntity
import com.linuxias.todolist.db.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoViewModel(private val repository: TodoRepository) : ViewModel() {
    val allTodoList: LiveData<List<TodoEntity>> = repository.allTodoList.asLiveData()

    fun insert(todo: TodoEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(todo)
    }

    fun delete(todo: TodoEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(todo)
    }
}

class TodoViewModelFactory(private val repository: TodoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TodoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}