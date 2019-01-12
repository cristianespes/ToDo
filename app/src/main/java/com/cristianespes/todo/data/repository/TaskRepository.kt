package com.cristianespes.todo.data.repository

import com.cristianespes.todo.data.model.Task
import io.reactivex.Single

interface TaskRepository {
    fun getAllTasks(): Single<List<Task>>
    fun getTaskById(taskId: Long): Single<Task>

    fun insert(task: Task)
    fun delete(task: Task)
    fun update(task: Task)
}