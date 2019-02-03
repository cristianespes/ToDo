package com.cristianespes.todo.ui.newtask

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import androidx.lifecycle.Observer
import com.cristianespes.todo.R
import com.cristianespes.todo.ui.base.BaseActivity
import com.cristianespes.todo.ui.viewmodel.TaskViewModel
import kotlinx.android.synthetic.main.activity_new_task.*
import org.koin.android.viewmodel.ext.android.viewModel

class NewTaskActivity : BaseActivity() {

    val taskViewModel: TaskViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_task)
        setUpToolbar(true)
        setTitle(R.string.new_task_title)

        bindObserver()
        bindActions()
    }

    private fun bindObserver() {
        with (taskViewModel) {
            newTaskAddedEvent.observe(this@NewTaskActivity, Observer {
                if (!it.hasBeenHandled) {
                    it.getContentIfNotHandled()

                    setResult(Activity.RESULT_OK)
                    finish()
                }
            })
        }
    }

    private fun bindActions() {
        buttonSaveTask.setOnClickListener {
            val textContent = inputTaskContent.text.toString()

            if (!textContent.isEmpty())
                taskViewModel.addNewTask(content = textContent, isHighPriority = checkHighPriority.isChecked)
            else
                androidx.appcompat.app.AlertDialog.Builder(this)
                    .setTitle(getString(R.string.task_empty))
                    .setMessage(getString(R.string.content_in_task))
                    .setPositiveButton(getString(R.string.accept), null)
                    .setNegativeButton(getString(R.string.cancel)) { _, _ ->
                        finish()
                    }
                    .create()
                    .show()
        }
    }

}