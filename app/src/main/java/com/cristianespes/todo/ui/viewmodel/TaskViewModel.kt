package com.cristianespes.todo.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.cristianespes.todo.data.model.Task
import com.cristianespes.todo.data.repository.TaskRepository
import com.cristianespes.todo.ui.base.BaseViewModel
import com.cristianespes.todo.util.Event
import com.cristianespes.todo.util.call
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.*

class TaskViewModel(val taskRepository: TaskRepository) : BaseViewModel() {

    val tasksEvent = MutableLiveData<List<Task>>()

    val newTaskAddedEvent = MutableLiveData<Event<Unit>>()
    val taskUpdatedEvent = MutableLiveData<Event<Task>>()

    init {
        loadTasks()
    }

    fun loadTasks() {
        taskRepository
            .observeAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { tasks ->
                    tasksEvent.value = tasks
                },
                onError = {
                    Log.e("TaskViewModel", "Error: $it")
                }
            ).addTo(compositeDisposable)
    }

    fun addNewTask(id: Long = 0, content: String, createdAt: Date = Date(), isDone: Boolean = false, isHighPriority: Boolean = false) {
        val newTask = Task(id, content, createdAt, isDone, isHighPriority)

        Completable.fromCallable {
            taskRepository.insert(newTask)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = {
                    newTaskAddedEvent.call()
                },
                onError = {
                    Log.e("TaskViewModel", "$it")
                }
            )
            .addTo(compositeDisposable)
    }

    fun deleteTask(task: Task) {
        Completable.fromCallable {
            taskRepository.delete(task)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = {
                },
                onError = {
                    Log.e("TaskViewModel", "$it")
                }
            )
            .addTo(compositeDisposable)
    }

    fun markAsDone(task: Task) {
        if (task.isDone) {
            return
        }

        val newTask = task.copy(isDone = true)
        updateTask(newTask)
    }

    fun markAsNotDone(task: Task) {
        if (!task.isDone) {
            return
        }

        val newTask = task.copy(isDone = false)
        updateTask(newTask)
    }

    fun markHighPriority(task: Task, highPriority: Boolean) {
        val newTask = task.copy(isHighPriority = highPriority)
        updateTask(newTask)
    }

    fun updateTask(task: Task) {
        Completable.fromCallable {
            taskRepository.update(task)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = {
                    taskUpdatedEvent.call(task)
                },
                onError = {
                    Log.e("TaskViewModel", "$it")
                }
            )
            .addTo(compositeDisposable)
    }

}