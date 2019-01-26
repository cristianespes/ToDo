package com.cristianespes.todo.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.cristianespes.todo.data.model.Subtask

class SubtaskDiffUtil: DiffUtil.ItemCallback<Subtask>() {

    companion object {
        fun getInstance(): SubtaskDiffUtil =
            SubtaskDiffUtil()
    }

    override fun areItemsTheSame(oldItem: Subtask, newItem: Subtask): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Subtask, newItem: Subtask): Boolean = oldItem == newItem
}