package com.project.sweettrackersample.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers

abstract class BaseViewModel : ViewModel() {

    private val disposable = CompositeDisposable()

    protected val _errorMsg = MutableLiveData<String>()
    val errorMsg: LiveData<String> get() = _errorMsg

    protected val _msg = MutableLiveData<String>()
    val msg: LiveData<String> get() = _msg

    protected val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    protected val exceptionDispatchers = Dispatchers.Main + coroutineExceptionHandler

    protected fun Disposable.addDisposable() {
        disposable.add(this)
    }

    override fun onCleared() {
        unBindViewModel()
        super.onCleared()
    }

    fun unBindViewModel() {
        disposable.clear()
    }
}