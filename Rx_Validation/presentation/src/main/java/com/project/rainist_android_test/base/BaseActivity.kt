package com.project.rainist_android_test.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.project.rainist_android_test.BR
import com.project.rainist_android_test.R
import com.project.rainist_android_test.ext.toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.BehaviorSubject


abstract class BaseActivity<B : ViewDataBinding, VM : BaseViewModel>(
    @LayoutRes private val layoutResId: Int
) : AppCompatActivity() {

    private val backPressSubject = BehaviorSubject.createDefault(0L)
    private val compositeDisposable = CompositeDisposable()
    protected abstract val vm: VM
    private lateinit var binding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@BaseActivity, layoutResId)
        bind {
            lifecycleOwner = this@BaseActivity
            setVariable(BR.vm, vm)
        }

        onClickBackButton()
    }

    protected fun bind(action: B.() -> Unit) {
        binding.run(action)
    }

    protected fun Disposable.addDisposable() {
        compositeDisposable.add(this)
    }

    override fun onDestroy() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
        super.onDestroy()
    }

    private fun onClickBackButton() {
        backPressSubject
            .subscribeOn(AndroidSchedulers.mainThread())
            .buffer(2, 1)
            .onErrorReturn { listOf(0, 0) }
            .subscribe {
                if (it[1] - it[0] < BACK_KEY_INTERVAL) {
                    onBackPressed()
                } else {
                    toast(getString(R.string.please_press_onemore))
                }
            }.addTo(compositeDisposable)
    }

    override fun onBackPressed() {
        backPressSubject.onNext(System.currentTimeMillis())
    }

    companion object {
        const val BACK_KEY_INTERVAL = 1500L
    }
}