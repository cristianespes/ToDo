package com.cristianespes.todo.ui.detailtask

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.os.ConfigurationCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cristianespes.todo.R
import com.cristianespes.todo.data.model.Subtask
import com.cristianespes.todo.data.model.Task
import com.cristianespes.todo.ui.adapter.SubtaskAdapter
import com.cristianespes.todo.ui.viewmodel.SubtaskViewModel
import com.cristianespes.todo.ui.viewmodel.TaskViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_detail_task.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat

class DetailTaskFragment: Fragment(), SubtaskAdapter.Listener {
    override fun onSubtaskClicked(subtask: Subtask) {
        Log.d("Patata", "onTaskClicked en ${subtask.content}")
    }

    override fun onSubtaskLongClicked(subtask: Subtask) {
        deleteSubtaskDialog(subtask)
    }

    override fun onSubtaskMarked(subtask: Subtask, isDone: Boolean) {
        if (isDone) {
            subtaskViewModel.markAsDone(subtask)
        } else {
            subtaskViewModel.markAsNotDone(subtask)
        }
    }

    companion object {
        const val PARAM_TASK = "task"

        fun newInstance(task: Task): DetailTaskFragment =
            DetailTaskFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(PARAM_TASK, task)
                }
            }
    }

    val taskViewModel: TaskViewModel by viewModel() // Lo tomamos del inyector de dependencias
    val subtaskViewModel: SubtaskViewModel by viewModel()

    val adapter: SubtaskAdapter by lazy {
        SubtaskAdapter(this)
    }

    var task: Task? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail_task, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        task = arguments?.let {
            it.getParcelable(PARAM_TASK)
        }

        task?.let {
            setUp()
        }?: run {
            activity!!.finish()
        }
    }

    private fun setUp() {

        titleDetailTask.text = task!!.content

        imageDetailTask.setImageResource(R.drawable.ic_task_calendar)
        dateDetailTask.text = SimpleDateFormat("dd/MM/yyyy", ConfigurationCompat.getLocales(resources.configuration)[0]).format(task!!.createdAt)

        checkIsDoneDetailTask.isChecked = task!!.isDone
        isDoneDetailTask.text = getString(R.string.finished)

        checkIsDoneDetailTask.setOnCheckedChangeListener { compoundButton, b ->
            val isDone = compoundButton.isChecked

            if (isDone) {
                taskViewModel.markAsDone(task!!)
            } else {
                taskViewModel.markAsNotDone(task!!)
            }
        }

        setUpRecycler()

        subtaskViewModel.loadSubtasksByTaskId(task!!.id)
        with (subtaskViewModel) {
            subtasksEvent.observe(this@DetailTaskFragment, Observer { subtasks ->
                adapter.submitList(subtasks)
            })
        }

    }

    private fun setUpRecycler() {
        recyclerSubtasks.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        recyclerSubtasks.itemAnimator = DefaultItemAnimator()
        recyclerSubtasks.adapter = adapter
    }

    fun updateTask(newText: String) {
        titleDetailTask.text = newText
    }

    private fun deleteSubtaskDialog(subtask: Subtask) {
        subtaskViewModel.deleteSubtask(subtask)

        Snackbar
            .make(fragment_detail_task, getString(R.string.subtask_deleted), Snackbar.LENGTH_LONG)
            .setAction(getString(R.string.undo)) {
                subtaskViewModel.addNewSubtask(subtask.content, subtask.taskId)
            }
            .show()
    }

}