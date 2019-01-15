package io.keepcoding.todo.ui.tasks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cristianespes.todo.R
import com.cristianespes.todo.data.model.Task
import kotlinx.android.synthetic.main.item_task.view.*

class TaskAdapter : ListAdapter<Task, TaskAdapter.TaskViewHolder>(TaskDiffUtil.getInstance()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TaskViewHolder(view: View): RecyclerView.ViewHolder(view) {

        fun bind(task: Task) {
            with (itemView) {
                textContent.text = task.content
                textDate.text = "${task.createdAt}"

                checkIsDone.isChecked = task.isDone
            }
        }

    }
}