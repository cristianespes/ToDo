package com.cristianespes.todo.ui.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    protected val compositeDisposable = CompositeDisposable()

    // Nos avisará cuando el Activity vaya a morir
    override fun onCleared() {
        super.onCleared()

        compositeDisposable.dispose()
    }
}