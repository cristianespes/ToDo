package com.cristianespes.todo.ui.editsubtask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.cristianespes.todo.R
import com.cristianespes.todo.data.model.Subtask
import com.cristianespes.todo.ui.viewmodel.SubtaskViewModel
import com.cristianespes.todo.util.BottomSheetDialog
import kotlinx.android.synthetic.main.fragment_edit_task.*
import org.koin.android.viewmodel.ext.android.viewModel

class EditSubtaskFragment: BottomSheetDialog() {

    companion object {
        const val PARAM_TASK = "subtask"

        fun newInstance(subtask: Subtask): EditSubtaskFragment =
            EditSubtaskFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(PARAM_TASK, subtask)
                }
            }
    }

    val subtaskViewModel: SubtaskViewModel by viewModel()

    var subtask: Subtask? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_edit_task, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subtask = arguments?.let {
            it.getParcelable(PARAM_TASK)
        }

        subtask?.let {
            setUp()
        }?: run {
            dismiss()
        }
    }

    private fun setUp() {
        fillData()
        bindEvents()
        bindActions()
    }

    private fun bindEvents() {
        with (subtaskViewModel) {
            subtaskUpdatedEvent.observe(this@EditSubtaskFragment, Observer {
                dismiss()
            })
        }
    }

    private fun fillData() {
        requireNotNull(subtask) {
            "Subtask is null dialog should be closed"
        }

        inputTaskContent.setText(subtask!!.content)
        checkHighPriority.visibility = View.GONE
    }

    private fun bindActions() {
        buttonSaveTask.setOnClickListener {
            val newSubtask = subtask!!.copy(
                content = inputTaskContent.text.toString())

            subtaskViewModel.updateSubtask(newSubtask)
        }
    }

}