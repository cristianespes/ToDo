package com.cristianespes.todo.ui.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cristianespes.todo.R
import io.keepcoding.todo.ui.tasks.TaskAdapter
import kotlinx.android.synthetic.main.fragment_tasks.*
import org.koin.android.viewmodel.ext.android.viewModel

class TaskFragment: Fragment() {

    val taskViewModel: TaskViewModel by viewModel() // Lo cojemos del inyector de dependencias

    val adapter: TaskAdapter by lazy {
        TaskAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tasks, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Aquí debemos inicializar lo relativo al ViewModel
        // taskviewModel = ViewModelProviders.of(this).get(TaskViewModel::class.java)
        // Pero no podemos inyectar dependencías de esta manera con koin

        setUp()
    }

    private fun setUp() {
        setUpRecycler()

        taskViewModel.tasksEvent.observe(this, Observer { tasks ->
            adapter.submitList(tasks)
        })
    }

    private fun setUpRecycler() {
        recyclerTasks.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        recyclerTasks.itemAnimator = DefaultItemAnimator()
        recyclerTasks.adapter = adapter
    }

}