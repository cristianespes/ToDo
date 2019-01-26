package com.cristianespes.todo.ui.edittask

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.cristianespes.todo.R
import com.cristianespes.todo.data.model.Task
import com.cristianespes.todo.ui.viewmodel.TaskViewModel
import com.cristianespes.todo.util.BottomSheetDialog
import org.koin.android.viewmodel.ext.android.viewModel
import kotlinx.android.synthetic.main.fragment_edit_task.*

class EditTaskFragment: BottomSheetDialog() {

    interface UpdatedTask {
        fun updatedTaskText(taskText: String)
    }

    var listener: UpdatedTask? = null

    companion object {
        const val PARAM_TASK = "task"

        fun newInstance(task: Task): EditTaskFragment =
            EditTaskFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(PARAM_TASK, task)
                }
            }
    }

    val taskViewModel: TaskViewModel by viewModel()

    var task: Task? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_edit_task, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        task = arguments?.let {
            it.getParcelable(PARAM_TASK)
        }
        if (task == null) {
            dismiss()
        }

        setUp()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is UpdatedTask) {
            listener = context // Inicializar el listener
        }
    }

    private fun setUp() {
        fillData()
        bindEvents()
        bindActions()
    }

    private fun bindEvents() {
        with (taskViewModel) {
            taskUpdatedEvent.observe(this@EditTaskFragment, Observer {
                listener?.updatedTaskText(inputTaskContent.text.toString())
                dismiss()
            })
        }
    }

    private fun fillData() {
        requireNotNull(task) {
            "Task is null dialog should be closed"
        }

        inputTaskContent.setText(task!!.content)
        checkHighPriority.isChecked = task!!.isHighPriority
    }

    private fun bindActions() {
        buttonSaveTask.setOnClickListener {
            val textContent = inputTaskContent.text.toString()

            if (!textContent.isEmpty()) {
                val newTask = task!!.copy(
                    content = inputTaskContent.text.toString(),
                    isHighPriority = checkHighPriority.isChecked)

                taskViewModel.updateTask(newTask)
            } else {
                androidx.appcompat.app.AlertDialog.Builder(activity!!)
                    .setTitle(getString(R.string.task_empty))
                    .setMessage(getString(R.string.content_in_task))
                    .setPositiveButton(getString(R.string.accept), null)
                    .setNegativeButton(getString(R.string.cancel)) { _, _ ->
                        dismiss()
                    }
                    .create()
                    .show()
            }
        }
    }

}