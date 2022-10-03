package com.linuxias.todolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.linuxias.todolist.databinding.ItemTodoBinding
import com.linuxias.todolist.db.TodoEntity

class TodoRecyclerbViewAdapter(private val listener : OnItemLongClinkListener)
    : ListAdapter<TodoEntity, TodoRecyclerbViewAdapter.TodoViewHolder>(TodoComparators()) {

    class TodoViewHolder(binding : ItemTodoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tv_importance = binding.tvImportance
        val tv_title = binding.tvTitle
        val root = binding.root
    }

    class TodoComparators : DiffUtil.ItemCallback<TodoEntity>() {
        override fun areItemsTheSame(oldItem: TodoEntity, newItem: TodoEntity): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: TodoEntity, newItem: TodoEntity): Boolean {
            return oldItem.id == newItem.id
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TodoRecyclerbViewAdapter.TodoViewHolder {
        val binding: ItemTodoBinding =
            ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoRecyclerbViewAdapter.TodoViewHolder, position: Int) {
        val todoData = getItem(position)
        when (todoData.importance) {
            1 -> {
                holder.tv_importance.setBackgroundResource(R.color.red)
            }
            2 -> {
                holder.tv_importance.setBackgroundResource(R.color.yellow)
            }
            3 -> {
                holder.tv_importance.setBackgroundResource(R.color.green)
            }
        }

        holder.tv_importance.text = todoData.importance.toString()
        holder.tv_title.text = todoData.title

        holder.root.setOnLongClickListener {
            val todoData = getItem(position)
            listener.onLongClick(todoData, position)
            false
        }
    }
}