package com.cristianespes.todo.data.repository

import com.cristianespes.todo.data.model.Subtask
import com.cristianespes.todo.data.repository.datasource.local.LocalSubtaskDataSource
import io.reactivex.Flowable
import io.reactivex.Single

class SubtaskRepositoryImpl(val localSubtaskDataSource: LocalSubtaskDataSource): SubtaskRepository {
    override fun getAllSubtask(): Single<List<Subtask>> = localSubtaskDataSource.getAllSubtask()

    override fun observeAllSubtask(): Flowable<List<Subtask>> = localSubtaskDataSource.observeAllSubtask()

    override fun getAllSubtaskByTaskId(taskId: Long): Single<List<Subtask>> = localSubtaskDataSource.getAllSubtaskByTaskId(taskId)

    override fun observeAllSubtaskByTaskId(taskId: Long): Flowable<List<Subtask>> = localSubtaskDataSource.observeAllSubtaskByTaskId(taskId)

    override fun getSubtaskById(id: Long): Single<Subtask> = localSubtaskDataSource.getSubtaskById(id)

    override fun insert(subtask: Subtask) {
        localSubtaskDataSource.insert(subtask)
    }

    override fun delete(subtask: Subtask) {
        localSubtaskDataSource.delete(subtask)
    }

    override fun update(subtask: Subtask) {
        localSubtaskDataSource.update(subtask)
    }
}