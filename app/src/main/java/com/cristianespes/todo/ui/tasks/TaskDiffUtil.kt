package com.cristianespes.todo.ui.tasks

import androidx.recyclerview.widget.DiffUtil
import com.cristianespes.todo.data.model.Task

class TaskDiffUtil : DiffUtil.ItemCallback<Task>() {

    companion object {
        fun getInstance(): TaskDiffUtil = TaskDiffUtil()
    }

    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean =
            oldItem == newItem

}