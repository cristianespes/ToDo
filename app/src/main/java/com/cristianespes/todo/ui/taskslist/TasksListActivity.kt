package com.cristianespes.todo.ui.taskslist

import android.os.Bundle
import com.cristianespes.todo.R
import com.cristianespes.todo.ui.base.BaseActivity
import com.cristianespes.todo.util.Navigator
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class TasksListActivity : BaseActivity() {

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Toolbar
        setUpToolbar(false)
        setTitle(R.string.app_name)

        setUp()
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    private fun setUp() {
        bindActions()

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, TasksListFragment())
            .commit()
    }

    private fun bindActions() {
        fab
            .clicks()
            .throttleFirst(500, TimeUnit.MILLISECONDS)
            .subscribe {
                Navigator.navigateToNewTaskActivity(this)
            }
            .addTo(compositeDisposable)
    }

}
