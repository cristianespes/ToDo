package com.cristianespes.todo.ui.detailtask

import android.os.Bundle
import android.view.*
import androidx.core.os.ConfigurationCompat
import androidx.fragment.app.Fragment
import com.cristianespes.todo.R
import com.cristianespes.todo.data.model.Task
import com.cristianespes.todo.ui.tasks.TaskViewModel
import kotlinx.android.synthetic.main.fragment_detail_task.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat

class DetailTaskFragment: Fragment() {

    companion object {
        const val PARAM_TASK = "task"

        fun newInstance(task: Task): DetailTaskFragment =
            DetailTaskFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(PARAM_TASK, task)
                }
            }
    }

    val taskViewModel: TaskViewModel by viewModel() // Lo cojemos del inyector de dependencias

    var task: Task? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail_task, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        task = arguments?.let {
            it.getParcelable(PARAM_TASK)
        }
        if (task == null) {
            // TODO: SI NO HAY TAREA
            //finish()
        }

        setUp()
    }

    private fun setUp() {

        titleDetailTask.text = task!!.content

        imageDetailTask.setImageResource(R.drawable.ic_task_calendar)
        dateDetailTask.text = SimpleDateFormat("dd/MM/yyyy", ConfigurationCompat.getLocales(resources.configuration)[0]).format(task!!.createdAt)

        checkIsDoneDetailTask.isChecked = task!!.isDone
        isDoneDetailTask.text = getString(R.string.finished)

        checkIsDoneDetailTask.setOnCheckedChangeListener { compoundButton, b ->
            val isDone = compoundButton.isChecked

            if (isDone) {
                taskViewModel.markAsDone(task!!)
            } else {
                taskViewModel.markAsNotDone(task!!)
            }
        }

    }

}