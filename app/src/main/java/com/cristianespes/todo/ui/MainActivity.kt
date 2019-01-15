package com.cristianespes.todo.ui

import android.os.Bundle
import com.cristianespes.todo.R
import com.cristianespes.todo.ui.base.BaseActivity
import com.cristianespes.todo.ui.tasks.TaskFragment

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUp()
    }

    private fun setUp() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, TaskFragment())
            .commit()

        /*supportFragmentManager.transaction {
            replace(R.id.fragmentContainer, TaskFragment())
        }*/
    }

}
