package com.cristianespes.todo.util

import android.content.Context
import android.content.Intent
import androidx.fragment.app.FragmentManager
import com.cristianespes.todo.data.model.Subtask
import com.cristianespes.todo.data.model.Task
import com.cristianespes.todo.ui.detailtask.DetailTaskActivity
import com.cristianespes.todo.ui.menusubtask.MenuSubtaskFragment
import com.cristianespes.todo.ui.menutask.MenuTaskFragment
import com.cristianespes.todo.ui.newtask.NewTaskActivity

object Navigator {

    fun navigateToNewTaskActivity(context: Context) {
        val intent = Intent(context, NewTaskActivity::class.java)
        context.startActivity(intent)
    }

    fun navigateToTaskMenuFragment(task: Task, fragmentManager: FragmentManager) {
        val fragment = MenuTaskFragment.newInstance(task)
        fragment.show(fragmentManager, null)
    }

    fun navigateToDetailTaskActivity(context: Context, task: Task) {
        val intent = Intent(context, DetailTaskActivity::class.java)
        intent.putExtra(DetailTaskActivity.PARAM_TASK, task)
        context.startActivity(intent)
    }

    fun navigateToSubtaskMenuFragment(subtask: Subtask, fragmentManager: FragmentManager) {
        val fragment = MenuSubtaskFragment.newInstance(subtask)
        fragment.show(fragmentManager, null)
    }
}