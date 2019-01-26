package com.cristianespes.todo.data.repository.datasource.local

import com.cristianespes.todo.data.model.Subtask
import com.cristianespes.todo.data.model.mapper.SubtaskEntityMapper
import com.cristianespes.todo.data.model.mapper.SubtaskMapper
import com.cristianespes.todo.data.repository.SubtaskDataSource
import io.reactivex.Flowable
import io.reactivex.Single

class LocalSubtaskDataSource(val toDoDatabase: ToDoDatabase,
                             val subtaskMapper: SubtaskMapper,
                             val subtaskEntityMapper: SubtaskEntityMapper): SubtaskDataSource {
    override fun getAllSubtask(): Single<List<Subtask>> =
        toDoDatabase
            .getSubtaskDao()
            .getAllSubtask()
            .map { subtaskMapper.transformList(it) }

    override fun observeAllSubtask(): Flowable<List<Subtask>> =
        toDoDatabase
            .getSubtaskDao()
            .observeAllSubtask()
            .map { subtaskMapper.transformList(it) }

    override fun getAllSubtaskByTaskId(taskId: Long): Single<List<Subtask>> =
        toDoDatabase
            .getSubtaskDao()
            .getAllSubtaskByTaskId(taskId)
            .map { subtaskMapper.transformList(it) }

    override fun observeAllSubtaskByTaskId(taskId: Long): Flowable<List<Subtask>> =
        toDoDatabase
            .getSubtaskDao()
            .observeAllSubtaskByTaskId(taskId)
            .map { subtaskMapper.transformList(it) }

    override fun getSubtaskById(id: Long): Single<Subtask> =
        toDoDatabase
            .getSubtaskDao()
            .getSubtaskById(id)
            .map { subtaskMapper.transform(it) }

    override fun insert(subtask: Subtask) {
        val subtaskEntity = subtaskEntityMapper.transform(subtask)

        toDoDatabase
            .getSubtaskDao()
            .insert(subtaskEntity)
    }

    override fun delete(task: Subtask) {
        val subtaskEntity = subtaskEntityMapper.transform(task)

        toDoDatabase
            .getSubtaskDao()
            .delete(subtaskEntity)
    }

    override fun update(subtask: Subtask) {
        val subtaskEntity = subtaskEntityMapper.transform(subtask)

        toDoDatabase
            .getSubtaskDao()
            .update(subtaskEntity)
    }

}