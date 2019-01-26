package com.cristianespes.todo.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.cristianespes.todo.data.model.Subtask
import com.cristianespes.todo.data.repository.SubtaskRepository
import com.cristianespes.todo.ui.base.BaseViewModel
import com.cristianespes.todo.util.Event
import com.cristianespes.todo.util.call
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class SubtaskViewModel(val subtaskRepository: SubtaskRepository) : BaseViewModel() {

    val subtasksEvent = MutableLiveData<List<Subtask>>()

    val newSubtaskAddedEvent = MutableLiveData<Event<Unit>>()
    val subtaskUpdatedEvent = MutableLiveData<Event<Subtask>>()

    fun loadSubtasksByTaskId(taskId: Long) {
        subtaskRepository
            .observeAllSubtaskByTaskId(taskId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { subtasks ->
                    subtasksEvent.value = subtasks
                },
                onError = {
                    Log.e("SubtaskViewModel", "Error: $it")
                }
            ).addTo(compositeDisposable)
    }

    fun addNewSubtask(subtaskContent: String, taskId: Long) {
        val newSubtask = Subtask(0, subtaskContent, false, taskId)

        Completable.fromCallable {
            subtaskRepository.insert(newSubtask)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = {
                    newSubtaskAddedEvent.call()
                },
                onError = {
                    Log.e("SubtaskViewModel", "$it")
                }
            )
            .addTo(compositeDisposable)
    }

    fun deleteSubtask(subtask: Subtask) {
        Completable.fromCallable {
            subtaskRepository.delete(subtask)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = {
                },
                onError = {
                    Log.e("SubtaskViewModel", "$it")
                }
            )
            .addTo(compositeDisposable)
    }

    fun markAsDone(subtask: Subtask) {
        if (subtask.isDone) {
            return
        }

        val newSubtask = subtask.copy(isDone = true)
        updateSubtask(newSubtask)
    }

    /*fun markAllSubtaskAsDone(subtaskList: List<Subtask>) {
        subtaskList.forEach { subtask ->
            val newSubtask = subtask.copy(isDone = true)
            updateSubtask(newSubtask)
        }
    }*/

    fun markAsNotDone(subtask: Subtask) {
        if (!subtask.isDone) {
            return
        }

        val newSubtask = subtask.copy(isDone = false)
        updateSubtask(newSubtask)
    }

    fun updateSubtask(subtask: Subtask) {
        Completable.fromCallable {
            subtaskRepository.update(subtask)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = {
                    subtaskUpdatedEvent.call(subtask)
                },
                onError = {
                    Log.e("SubtaskViewModel", "$it")
                }
            )
            .addTo(compositeDisposable)
    }

}