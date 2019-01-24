package com.cristianespes.todo.ui.detailtask

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.core.os.ConfigurationCompat
import com.cristianespes.todo.R
import com.cristianespes.todo.data.model.Task
import com.cristianespes.todo.ui.base.BaseActivity
import com.cristianespes.todo.ui.tasks.TaskViewModel
import com.cristianespes.todo.util.Navigator
import kotlinx.android.synthetic.main.activity_detail_task.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat

class DetailTaskActivity : BaseActivity() {

    companion object {
        const val PARAM_TASK = "task"
    }

    val taskViewModel: TaskViewModel by viewModel()

    lateinit var task: Task

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_task)

        setUpToolbar(true)
        setTitle(R.string.detail_task_title)

        val bundle = intent.extras

        if (bundle != null) {
            task = bundle.get(DetailTaskActivity.PARAM_TASK) as Task

            Log.d("Patata", "Tarea ${task.content}")
        }

        setupViews()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu to use in the action bar
        menuInflater.inflate(R.menu.toolbar_items, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        item.takeIf { item?.itemId == R.id.action_edit }?.let { _ ->
            // TODO: IMPLEMENTNAR EDITAR
        }

        item.takeIf { item?.itemId == R.id.action_delete }?.let { _ ->
            showConfirmDeleteTaskDialog()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setupViews() {
        titleDetailTask.text = task.content

        imageDetailTask.setImageResource(R.drawable.ic_task_calendar)
        dateDetailTask.text = SimpleDateFormat("dd/MM/yyyy", ConfigurationCompat.getLocales(resources.configuration)[0]).format(task.createdAt)

        checkIsDoneDetailTask.isChecked = task.isDone
        isDoneDetailTask.text = getString(R.string.finished)

        checkIsDoneDetailTask.setOnCheckedChangeListener { compoundButton, b ->
            val isDone = compoundButton.isChecked

            if (isDone) {
                taskViewModel.markAsDone(task)
            } else {
                taskViewModel.markAsNotDone(task)
            }
        }
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
