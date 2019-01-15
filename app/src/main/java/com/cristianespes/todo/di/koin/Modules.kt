package com.cristianespes.todo.di.koin

import androidx.room.Room
import com.cristianespes.todo.data.model.mapper.TaskEntityMapper
import com.cristianespes.todo.data.model.mapper.TaskMapper
import com.cristianespes.todo.data.repository.TaskRepository
import com.cristianespes.todo.data.repository.TaskRepositoryImpl
import com.cristianespes.todo.data.repository.datasource.local.LocalDataSource
import com.cristianespes.todo.data.repository.datasource.local.ToDoDatabase
import com.cristianespes.todo.ui.tasks.TaskViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModule = module {

    // single -> Se crea instancia una única vez
    // factory -> Se crea instancia cada vez que se llama

    single { TaskMapper() }

    single { TaskEntityMapper() }

    single<ToDoDatabase>  { Room.databaseBuilder(androidContext(), ToDoDatabase::class.java, "todo.db").build() }

    single { LocalDataSource(get(), get(), get()) }

    single<TaskRepository> { TaskRepositoryImpl(get()) }

    // Inyectamos dependencias a Fragment haciendo uso de la librería  koin-viewModel
    viewModel { TaskViewModel(get()) }

}