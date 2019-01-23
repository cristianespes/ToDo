package com.cristianespes.todo.ui.detailtask

import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.core.os.ConfigurationCompat
import com.cristianespes.todo.R
import com.cristianespes.todo.data.model.Task
import com.cristianespes.todo.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_detail_task.*
import java.text.SimpleDateFormat

class DetailTaskActivity : BaseActivity() {

    companion object {
        const val PARAM_TASK = "task"
    }

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

    private fun setupViews() {
        titleDetailTask.text = task.content

        imageDetailTask.setImageResource(R.drawable.ic_task_calendar)
        dateDetailTask.text = SimpleDateFormat("dd/MM/yyyy", ConfigurationCompat.getLocales(resources.configuration)[0]).format(task.createdAt)

        checkIsDoneDetailTask.isChecked = task.isDone
        isDoneDetailTask.text = getString(R.string.finished)
    }
}
