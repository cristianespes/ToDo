package com.cristianespes.todo.ui.detailtask

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.os.ConfigurationCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cristianespes.todo.R
import com.cristianespes.todo.data.model.Subtask
import com.cristianespes.todo.data.model.Task
import com.cristianespes.todo.ui.adapter.SubtaskAdapter
import com.cristianespes.todo.ui.monitoring.MonitoringSubtaskFragment
import com.cristianespes.todo.ui.viewmodel.SubtaskViewModel
import com.cristianespes.todo.ui.viewmodel.TaskViewModel
import com.cristianespes.todo.util.Navigator
import com.cristianespes.todo.util.bottomsheet.BottomMenuItem
import com.cristianespes.todo.util.bottomsheet.BottomSheetMenu
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.fragment_detail_task.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit

class DetailTaskFragment: Fragment(), SubtaskAdapter.Listener {

    interface UpdateTableData {
        fun onSubtaskListChanged(subtaskList: List<Subtask>)
    }

    override fun onSubtaskClicked(subtask: Subtask) {
        Navigator.navigateToEditSubtaskFragment(subtask, childFragmentManager)
    }

    override fun onSubtaskLongClicked(subtask: Subtask) {
        val items = arrayListOf(
            BottomMenuItem(R.drawable.ic_delete, getString(R.string.delete)) {
                deleteSubtaskDialog(subtask)
            }
        )

        BottomSheetMenu(activity!!, items).show()
    }

    override fun onSubtaskMarked(subtask: Subtask, isDone: Boolean) {
        if (isDone) {
            subtaskViewModel.markAsDone(subtask)
        } else {
            subtaskViewModel.markAsNotDone(subtask)
        }
    }

    companion object {
        const val PARAM_TASK = "task"

        fun newInstance(task: Task): DetailTaskFragment =
            DetailTaskFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(PARAM_TASK, task)
                }
            }
    }

    val taskViewModel: TaskViewModel by viewModel() // Lo tomamos del inyector de dependencias
    val subtaskViewModel: SubtaskViewModel by viewModel()

    private lateinit var monitoringFragment: MonitoringSubtaskFragment

    private val compositeDisposable = CompositeDisposable()

    val adapter: SubtaskAdapter by lazy {
        SubtaskAdapter(this)
    }

    var task: Task? = null
    var subtaskList: MutableList<Subtask> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        monitoringFragment = MonitoringSubtaskFragment()

        childFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_monitoring, monitoringFragment)
            .commit()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        task = arguments?.let {
            it.getParcelable(PARAM_TASK)
        }

        task?.let {
            setUp()
        }?: run {
            activity!!.finish()
        }
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
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

        setUpRecycler()

        subtaskViewModel.loadSubtasksByTaskId(task!!.id)
        with (subtaskViewModel) {
            subtasksEvent.observe(this@DetailTaskFragment, Observer { subtasks ->
                adapter.submitList(subtasks)
                subtaskList = subtasks.toMutableList()


                monitoringFragment.updateTable(subtasks)
            })
        }

        bindActions()
    }

    private fun setUpRecycler() {
        recyclerSubtasks.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        recyclerSubtasks.itemAnimator = DefaultItemAnimator()
        recyclerSubtasks.adapter = adapter
    }

    fun updateTask(newText: String) {
        titleDetailTask.text = newText
    }

    private fun deleteSubtaskDialog(subtask: Subtask) {
        subtaskViewModel.deleteSubtask(subtask)

        Snackbar
            .make(fragment_detail_task, getString(R.string.subtask_deleted), Snackbar.LENGTH_LONG)
            .setAction(getString(R.string.undo)) {
                subtaskViewModel.addNewSubtask(subtask.content, subtask.taskId)
            }
            .show()
    }

    private fun bindActions() {
        addSubtaskButton
            .clicks()
            .throttleFirst(500, TimeUnit.MILLISECONDS)
            .subscribe {

                Log.d("PATATA", "AÑADIR UNA NUEVA NOTA")

                Navigator.navigateToAddNewSubtaskFragment(task!!.id, childFragmentManager)

            }.addTo(compositeDisposable)
    }
}