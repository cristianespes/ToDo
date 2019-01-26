package com.cristianespes.todo.data.repository.datasource.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "subtasks",
    foreignKeys = [
        ForeignKey(entity = TaskEntity::class,
            parentColumns = ["id"],
            childColumns = ["taskId"],
            onDelete = CASCADE)])

data class SubtaskEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val content: String,
    @ColumnInfo(name = "is_done")
    val isDone: Boolean,
    @ColumnInfo(name = "taskId")
    val taskId: Long
)