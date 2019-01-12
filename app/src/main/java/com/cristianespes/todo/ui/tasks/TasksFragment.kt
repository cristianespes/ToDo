package com.cristianespes.todo.ui.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.cristianespes.todo.R
import org.koin.android.viewmodel.ext.android.viewModel

class TasksFragment: Fragment() {

    val taskViewModel: TaskViewModel by viewModel() // Lo cojemos del inyector de dependencias

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tasks, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Aquí debemos inicializar lo relativo al ViewModel
        // taskviewModel = ViewModelProviders.of(this).get(TaskViewModel::class.java)
        // Pero no podemos inyectar dependencías de esta manera con koin
    }

}