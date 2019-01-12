package com.cristianespes.todo.ui

import android.app.Application
import com.cristianespes.todo.di.koin.appModule
import org.koin.android.ext.android.startKoin

class ToDoApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this, modules = listOf(appModule))
    }

}