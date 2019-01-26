package com.cristianespes.todo.data.repository.datasource.local

import androidx.room.*
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
abstract class SubtaskDao {

    @Query("SELECT * FROM subtasks ORDER BY id DESC")
    abstract fun getAllSubtask(): Single<List<SubtaskEntity>>

    @Query("SELECT * FROM subtasks ORDER BY id DESC")
    abstract fun observeAllSubtask(): Flowable<List<SubtaskEntity>>

    @Query("SELECT * FROM subtasks WHERE taskId = :taskId")
    abstract fun getSubtaskByTaskId(taskId: Long): Single<SubtaskEntity>

    @Insert
    abstract fun insert(subtaskEntity: SubtaskEntity)

    @Delete
    abstract fun delete(subtaskEntity: SubtaskEntity)

    @Update
    abstract fun update(subtaskEntity: SubtaskEntity)

}