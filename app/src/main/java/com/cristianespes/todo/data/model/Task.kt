package com.cristianespes.todo.data.model

import java.util.*

data class Task (
    val id: Long,
    val content: String,
    val createdAt: Date,
    val isDone: Boolean
)