package com.project.baetory_rx_validation.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    protected fun Disposable.addDisposable() {
        compositeDisposable.add(this)
    }

    override fun onCleared() {
        unBindViewModel()
        super.onCleared()
    }

    private fun unBindViewModel() {
        compositeDisposable.clear()
    }
}