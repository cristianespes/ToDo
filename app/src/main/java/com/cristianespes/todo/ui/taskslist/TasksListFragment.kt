package com.cristianespes.todo.ui.taskslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cristianespes.todo.R
import com.cristianespes.todo.data.model.Task
import com.cristianespes.todo.ui.adapter.TaskAdapter
import com.cristianespes.todo.ui.viewmodel.SubtaskViewModel
import com.cristianespes.todo.ui.viewmodel.TaskViewModel
import com.cristianespes.todo.util.Navigator
import com.cristianespes.todo.util.bottomsheet.BottomMenuItem
import com.cristianespes.todo.util.bottomsheet.BottomSheetMenu
import kotlinx.android.synthetic.main.fragment_tasks.*
import org.koin.android.viewmodel.ext.android.viewModel

class TasksListFragment: Fragment(), TaskAdapter.Listener {

    val taskViewModel: TaskViewModel by viewModel() // Lo cojemos del inyector de dependencias
    val subtaskViewModel: SubtaskViewModel by viewModel()

    val adapter: TaskAdapter by lazy {
        TaskAdapter(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tasks, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Aquí debemos inicializar lo relativo al ViewModel
        // taskviewModel = ViewModelProviders.of(this).get(TaskViewModel::class.java)
        // Pero no podemos inyectar dependencías de esta manera con koin

        setUp()
    }

    private fun setUp() {
        setUpRecycler()

        with (taskViewModel) {
            tasksEvent.observe(this@TasksListFragment, Observer { tasks ->
                adapter.submitList(tasks)
            })
        }
    }

    private fun setUpRecycler() {
        recyclerTasks.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        recyclerTasks.itemAnimator = DefaultItemAnimator()
        recyclerTasks.adapter = adapter
    }

    override fun onTaskClicked(task: Task) {
        Navigator.navigateToDetailTaskActivity(activity!!, task)
    }

    override fun onTaskLongClicked(task: Task) {
        val items = arrayListOf(
            BottomMenuItem(R.drawable.ic_edit, getString(R.string.edit)) {
                Navigator.navigateToTaskMenuFragment(task, childFragmentManager)
            },
            BottomMenuItem(R.drawable.ic_delete, getString(R.string.delete)) {
                showConfirmDeleteTaskDialog(task)
            }
        )

        BottomSheetMenu(activity!!, items).show()
    }

    private fun showConfirmDeleteTaskDialog(task: Task) {
        AlertDialog.Builder(activity!!)
            .setTitle(R.string.delete_task_title)
            .setMessage(R.string.delete_task_message)
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                taskViewModel.deleteTask(task)
            }
            .setNegativeButton(getString(R.string.no), null)
            .create()
            .show()
    }

    override fun onTaskMarked(task: Task, isDone: Boolean) {
        if (isDone) {
            taskViewModel.markAsDone(task)
        } else {
            taskViewModel.markAsNotDone(task)
        }
    }

    override fun onTaskHighPriorityMarked(task: Task, isHighPriority: Boolean) {
        taskViewModel.markHighPriority(task, isHighPriority)
    }
}