package com.cristianespes.todo.ui.monitoring

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cristianespes.todo.R
import com.cristianespes.todo.data.model.Task
import kotlinx.android.synthetic.main.fragment_monitoring_task.*

class MonitoringTaskFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_monitoring_task, container, false)
    }

    fun updateTable(taskList: List<Task>) {
        val total = taskList.count()
        var todo = 0
        var priority = 0
        taskList.forEach {
            if (it.isDone == false) { todo += 1 }
            if (it.isHighPriority == true) { priority += 1 }
        }

        totalValue.text = total.toString()
        doneValue.text = (total - todo).toString()
        todoValue.text = todo.toString()
        priorityValue.text = priority.toString()
    }
}