package com.cristianespes.todo.data.repository.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [TaskEntity::class], version = 1)
@TypeConverters(com.cristianespes.todo.data.repository.datasource.local.TypeConverters::class)
abstract class ToDoDatabase: RoomDatabase() {

    abstract fun getTaskDao(): TaskDao

}