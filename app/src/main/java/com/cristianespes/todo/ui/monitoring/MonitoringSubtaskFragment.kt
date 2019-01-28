package com.cristianespes.todo.ui.monitoring

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cristianespes.todo.R
import com.cristianespes.todo.data.model.Subtask
import kotlinx.android.synthetic.main.fragment_monitoring_subtask.*

class MonitoringSubtaskFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_monitoring_subtask, container, false)
    }

    fun updateTable(subtaskList: List<Subtask>) {
        val total = subtaskList.count()
        var todo = 0
        subtaskList.forEach {
            if (it.isDone == false) { todo += 1 }
        }

        priorityLinearLayout.visibility = View.GONE
        priorityLinearView.visibility = View.GONE

        totalValue.text = total.toString()
        doneValue.text = (total - todo).toString()
        todoValue.text = todo.toString()
    }

}