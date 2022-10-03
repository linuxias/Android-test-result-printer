package com.linuxias.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.linuxias.todolist.databinding.ActivityAddTodoBinding
import com.linuxias.todolist.db.TodoDao
import com.linuxias.todolist.db.TodoDatabase
import com.linuxias.todolist.db.TodoEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext

class AddTodoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTodoBinding
    private val todoViewModel: TodoViewModel by viewModels {
        TodoViewModelFactory((application as TodoApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnComplete.setOnClickListener {
            insertTodo()
        }
    }

    override fun onResume() {
        super.onResume()
    }

    private fun insertTodo() {
        val todoTitle = binding.edtTitle.text.toString()
        var todoImportance = binding.radioGroup.checkedRadioButtonId

        when (todoImportance) {
            R.id.btn_high -> {
                todoImportance = 1
            }
            R.id.btn_middle -> {
                todoImportance = 2
            }
            R.id.btn_low -> {
                todoImportance = 3
            }
        }

        if (todoImportance == -1 || todoTitle.isBlank()) {
            Toast.makeText(this,
                "Please fill title and importance",
                Toast.LENGTH_LONG).show()
        } else {
            Thread {
                todoViewModel.insert(TodoEntity(null, todoTitle, todoImportance))
                runOnUiThread {
                    Toast.makeText(this, "Compelete to add todo",
                    Toast.LENGTH_SHORT).show()
                    finish()
                }
            }.start()
        }
    }
}