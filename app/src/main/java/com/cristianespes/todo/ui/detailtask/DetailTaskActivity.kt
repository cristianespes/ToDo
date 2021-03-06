package com.cristianespes.todo.ui.detailtask

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import com.cristianespes.todo.R
import com.cristianespes.todo.data.model.Task
import com.cristianespes.todo.ui.base.BaseActivity
import com.cristianespes.todo.ui.edittask.EditTaskFragment
import com.cristianespes.todo.ui.monitoring.MonitoringSubtaskFragment
import com.cristianespes.todo.ui.viewmodel.TaskViewModel
import com.cristianespes.todo.util.Navigator
import org.koin.android.viewmodel.ext.android.viewModel

class DetailTaskActivity : BaseActivity(), EditTaskFragment.UpdatedTask {
    override fun updatedTaskText(taskText: String) {
        detailTaskFragment.updateTask(taskText)
    }

    companion object {
        const val PARAM_TASK = "task"
    }

    val taskViewModel: TaskViewModel by viewModel()

    lateinit var task: Task

    private lateinit var detailTaskFragment: DetailTaskFragment
    private lateinit var monitoringSubtaskFragment: MonitoringSubtaskFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_task)

        setUpToolbar(true)
        setTitle(R.string.detail_task_title)

        val bundle = intent.extras

        if (bundle != null) {
            task = bundle.get(DetailTaskActivity.PARAM_TASK) as Task
        }

        setUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu to use in the action bar
        menuInflater.inflate(R.menu.toolbar_items, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        item.takeIf { item?.itemId == R.id.action_edit }?.let { _ ->
            Navigator.navigateToTaskMenuFragment(task, supportFragmentManager)
        }

        item.takeIf { item?.itemId == R.id.action_delete }?.let { _ ->
            showConfirmDeleteTaskDialog()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setUp() {
        detailTaskFragment = DetailTaskFragment.newInstance(task)
        monitoringSubtaskFragment = MonitoringSubtaskFragment()

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, detailTaskFragment)
            .commit()
    }

    private fun showConfirmDeleteTaskDialog() {
        AlertDialog.Builder(this)
            .setTitle(R.string.delete_task_title)
            .setMessage(R.string.delete_task_message)
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                taskViewModel.deleteTask(task)
                finish()
            }
            .setNegativeButton(getString(R.string.no), null)
            .create()
            .show()
    }

}
