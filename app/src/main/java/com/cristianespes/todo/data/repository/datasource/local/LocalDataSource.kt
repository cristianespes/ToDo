package com.cristianespes.todo.data.repository.datasource.local

import com.cristianespes.todo.data.model.Task
import com.cristianespes.todo.data.model.mapper.TaskEntityMapper
import com.cristianespes.todo.data.model.mapper.TaskMapper
import com.cristianespes.todo.data.repository.TaskDataSource
import io.reactivex.Flowable
import io.reactivex.Single

class LocalDataSource(val toDoDatabase: ToDoDatabase,
                      val taskMapper: TaskMapper,
                      val taskEntityMapper: TaskEntityMapper): TaskDataSource {
    override fun getAll(): Single<List<Task>> =
        toDoDatabase
            .getTaskDao()
            .getAll()
            .map { taskMapper.transformList(it) }

    override fun observeAll(): Flowable<List<Task>> =
        toDoDatabase
            .getTaskDao()
            .observeAll()
            .map { taskMapper.transformList(it) }

    override fun getTaskById(taskId: Long): Single<Task> =
        toDoDatabase
            .getTaskDao()
            .getById(taskId)
            .map { taskMapper.transform(it) }

    override fun insert(task: Task) {
        val taskEntity = taskEntityMapper.transform(task)

        toDoDatabase
            .getTaskDao()
            .insert(taskEntity)

        // Eliminar
        /*toDoDatabase
            .getSubtaskDao()
            .insert(SubtaskEntity(1, "Subtarea de la tarea", false, 1))*/
    }

    override fun delete(task: Task) {
        val taskEntity = taskEntityMapper.transform(task)

        toDoDatabase
            .getTaskDao()
            .delete(taskEntity)
    }

    override fun update(task: Task) {
        val taskEntity = taskEntityMapper.transform(task)

        toDoDatabase
            .getTaskDao()
            .update(taskEntity)
    }

}