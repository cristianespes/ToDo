package com.cristianespes.todo.ui.newtask

import android.app.Activity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.cristianespes.todo.R
import com.cristianespes.todo.ui.base.BaseActivity
import com.cristianespes.todo.ui.tasks.TaskViewModel
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
            taskViewModel.addNewTask(inputTaskContent.text.toString(), checkHighPriority.isChecked)
        }
    }

}