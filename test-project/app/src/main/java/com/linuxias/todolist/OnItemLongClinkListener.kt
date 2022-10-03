package com.linuxias.todolist

import com.linuxias.todolist.db.TodoEntity

interface OnItemLongClinkListener {
    fun onLongClick(todoData : TodoEntity, position : Int)
}