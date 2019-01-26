package com.cristianespes.todo.data.model.mapper

import com.cristianespes.todo.data.model.Subtask
import com.cristianespes.todo.data.repository.datasource.local.SubtaskEntity

class SubtaskEntityMapper: Mapper<Subtask, SubtaskEntity> {
    override fun transform(input: Subtask): SubtaskEntity = SubtaskEntity(
        input.id,
        input.content,
        input.isDone,
        input.taskId
    )

    override fun transformList(input: List<Subtask>): List<SubtaskEntity> = input.map { transform(it) }
}