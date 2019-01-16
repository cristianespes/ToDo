package com.cristianespes.todo.util

import android.content.Context
import android.content.Intent
import com.cristianespes.todo.ui.newtask.NewTaskActivity

object Navigator {

    fun navigateToNewTaskActivity(context: Context) {
        val intent = Intent(context, NewTaskActivity::class.java)
        context.startActivity(intent)
    }

}