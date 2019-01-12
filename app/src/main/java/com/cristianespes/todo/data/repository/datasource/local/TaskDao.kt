package com.cristianespes.todo.data.repository.datasource.local

import androidx.room.*
import io.reactivex.Single

@Dao
abstract class TaskDao {

    @Query("SELECT * FROM tasks")
    abstract fun getAll(): Single<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE id = :id")
    abstract fun getById(id: Long): Single<TaskEntity>

    @Insert
    abstract fun insert(taskEntity: TaskEntity)

    @Delete
    abstract fun delete(taskEntity: TaskEntity)

    @Update
    abstract fun update(taskEntity: TaskEntity)

}