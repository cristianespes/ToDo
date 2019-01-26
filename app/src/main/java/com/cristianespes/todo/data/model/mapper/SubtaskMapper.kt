package com.cristianespes.todo.data.model.mapper

import com.cristianespes.todo.data.model.Subtask
import com.cristianespes.todo.data.repository.datasource.local.SubtaskEntity

class SubtaskMapper: Mapper<SubtaskEntity, Subtask> {
    override fun transform(input: SubtaskEntity): Subtask = Subtask(
        input.id,
        input.content,
        input.isDone,
        input.taskId
    )

    override fun transformList(input: List<SubtaskEntity>): List<Subtask> = input.map { transform(it) }
}