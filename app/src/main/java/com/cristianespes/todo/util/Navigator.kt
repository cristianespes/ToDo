package com.cristianespes.todo.util

import android.content.Context
import android.content.Intent
import androidx.fragment.app.FragmentManager
import com.cristianespes.todo.data.model.Task
import com.cristianespes.todo.ui.edittask.EditTaskFragment
import com.cristianespes.todo.ui.newtask.NewTaskActivity

object Navigator {

    fun navigateToNewTaskActivity(context: Context) {
        val intent = Intent(context, NewTaskActivity::class.java)
        context.startActivity(intent)
    }

    fun navigateToEditTaskFragment(task: Task, fragmentManager: FragmentManager) {
        val fragment = EditTaskFragment.newInstance(task)
        fragment.show(fragmentManager, null)
    }
}