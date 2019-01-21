package com.cristianespes.todo.ui.detailtask

import android.os.Bundle
import android.util.Log
import com.cristianespes.todo.R
import com.cristianespes.todo.data.model.Task
import com.cristianespes.todo.ui.base.BaseActivity
import com.cristianespes.todo.ui.edittask.EditTaskFragment

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
    }
}
