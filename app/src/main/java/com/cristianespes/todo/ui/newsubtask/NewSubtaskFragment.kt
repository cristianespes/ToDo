package com.cristianespes.todo.ui.newsubtask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cristianespes.todo.R
import com.cristianespes.todo.ui.viewmodel.SubtaskViewModel
import com.cristianespes.todo.util.BottomSheetDialog
import kotlinx.android.synthetic.main.fragment_edit_task.*
import org.koin.android.viewmodel.ext.android.viewModel

class NewSubtaskFragment: BottomSheetDialog() {

    companion object {
        const val PARAM_TASK = "taskId"

        fun newInstance(taskId: Long): NewSubtaskFragment =
            NewSubtaskFragment().apply {
                arguments = Bundle().apply {
                    putLong(PARAM_TASK, taskId)
                }
            }
    }

    val subtaskViewModel: SubtaskViewModel by viewModel()

    var taskId: Long? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_edit_task, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        taskId = arguments?.let {
            it.getLong(PARAM_TASK)
        }

        taskId?.let {
            setUp()
        }?: run {
            dismiss()
        }
    }

    private fun setUp() {
        checkHighPriority.visibility = View.GONE

        bindActions()
    }

    private fun bindActions() {
        buttonSaveTask.setOnClickListener {
            subtaskViewModel.addNewSubtask(inputTaskContent.text.toString(), taskId!!)
            dismiss()
        }
    }

}