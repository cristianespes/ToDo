package com.cristianespes.todo.data.model.mapper

import com.cristianespes.todo.data.model.Task
import com.cristianespes.todo.data.repository.datasource.local.TaskEntity

class TaskMapper: Mapper<TaskEntity, Task> {
    override fun transform(input: TaskEntity): Task = Task(
        input.id,
        input.content,
        input.createdAt,
        input.isDone
    )

    override fun transformList(input: List<TaskEntity>): List<Task> = input.map { transform(it) }
}