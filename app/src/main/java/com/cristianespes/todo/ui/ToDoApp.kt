package com.cristianespes.todo.ui

import android.app.Application
import com.cristianespes.todo.di.koin.appModule
import com.facebook.stetho.Stetho
import org.koin.android.ext.android.startKoin

class ToDoApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this, modules = listOf(appModule))

        Stetho.initializeWithDefaults(this)
    }

}