package com.cristianespes.todo.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.recyclerview.widget.ListAdapter
import com.cristianespes.todo.R
import com.cristianespes.todo.data.model.Task
import com.cristianespes.todo.ui.base.BaseViewholder
import com.cristianespes.todo.util.DateHelper
import kotlinx.android.synthetic.main.item_task.view.*

class TaskAdapter(val listener: Listener) : ListAdapter<Task, TaskAdapter.TaskViewHolder>(TaskDiffUtil.getInstance()) {

    interface Listener {
        fun onTaskClicked(task: Task)
        fun onTaskMarked(task: Task, isDone: Boolean)
        fun onTaskLongClicked(task: Task)
        fun onTaskHighPriorityMarked(task: Task, isHighPriority: Boolean)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TaskViewHolder(view: View) : BaseViewholder<Task>(view) {

        override fun bind(item: Task) {
            with(itemView) {

                if (item.isDone) {
                    applyStrikethrough(textContent, item.content)
                } else {
                    removeStrikethrough(textContent, item.content)
                }

                applyColorToHighPriority(itemView.findViewById(R.id.buttonHighPriority), item.isHighPriority)

                textDate.text = DateHelper.calculateTimeAgo(item.createdAt)

                checkIsDone.isChecked = item.isDone

                setOnClickListener {
                    listener.onTaskClicked(item)
                }

                setOnLongClickListener {
                    listener.onTaskLongClicked(item)
                    true
                }

                checkIsDone.setOnClickListener {
                    val isChecked = (it as CheckBox).isChecked

                    listener.onTaskMarked(item, isChecked)

                    it.animate()
                        .rotationBy(360f)
                        .setDuration(300)
                        .setInterpolator(FastOutSlowInInterpolator())
                        .start()

                    val textContent = itemView.findViewById<TextView>(R.id.textContent)
                    executeAnimation(textContent, isChecked)
                }

                buttonHighPriority.setOnClickListener {
                    val isHighPriority = !item.isHighPriority

                    listener.onTaskHighPriorityMarked(item, isHighPriority)
                }
            }
        }

    }

}
