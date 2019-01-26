package com.cristianespes.todo.data.repository

import com.cristianespes.todo.data.model.Subtask
import io.reactivex.Flowable
import io.reactivex.Single

interface SubtaskDataSource {
    fun getAllSubtask(): Single<List<Subtask>>
    fun observeAllSubtask(): Flowable<List<Subtask>>
    fun getAllSubtaskByTaskId(taskId: Long): Single<List<Subtask>>
    fun observeAllSubtaskByTaskId(taskId: Long): Flowable<List<Subtask>>
    fun getSubtaskById(id: Long): Single<Subtask>

    fun insert(subtask: Subtask)
    fun delete(subtask: Subtask)
    fun update(subtask: Subtask)
}