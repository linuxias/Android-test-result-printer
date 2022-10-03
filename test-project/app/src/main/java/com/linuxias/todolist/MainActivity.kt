package com.linuxias.todolist

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.linuxias.todolist.databinding.ActivityMainBinding
import com.linuxias.todolist.db.TodoEntity

class MainActivity : AppCompatActivity(), OnItemLongClinkListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TodoRecyclerbViewAdapter

    private val todoViewModel: TodoViewModel by viewModels {
        TodoViewModelFactory((application as TodoApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAdd.setOnClickListener {
            startActivity(Intent(this, AddTodoActivity::class.java))
        }

        getAllTodoList()
    }

    private fun getAllTodoList() {
        todoViewModel.allTodoList.observe(this) { it ->
            it.let { adapter.submitList(it)}
        }
        setRecycleView()
    }

    private fun setRecycleView() {
        adapter = TodoRecyclerbViewAdapter(this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onRestart() {
        super.onRestart()
        getAllTodoList()
    }

    override fun onLongClick(todoData: TodoEntity, position: Int) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Delete todo item")
        builder.setMessage("Would you want to delete item?")
        builder.setNegativeButton("No", null)
        builder.setPositiveButton("Yes",
            object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    deleteTodo(todoData, position)
                }
            }
        )
        builder.show()
    }

    private fun deleteTodo(todoData: TodoEntity, position: Int) {
        Thread {
            todoViewModel.delete(todoData)
            runOnUiThread {
                adapter.notifyItemRemoved(position)
                Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show()
            }
        }.start()
    }
}