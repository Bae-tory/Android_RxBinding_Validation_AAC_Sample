package com.project.rainist_android_test.component

import android.view.View
import com.project.rainist_android_test.ui.signup.SignUpActivity.Companion.THROTTLE_TIME
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

typealias ClickListener = (View) -> Unit

class ThrottleFirstClickListener(
    private val listener: ClickListener
) : View.OnClickListener {

    private val compositeDisposable = CompositeDisposable()
    private val onClickListenerBehaviorSubject = PublishSubject.create<View>()

    init {
        onClickListenerBehaviorSubject.throttleFirst(THROTTLE_TIME, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { it.run(listener) }
            .addTo(compositeDisposable)
    }

    override fun onClick(view: View) {
        onClickListenerBehaviorSubject.onNext(view)
    }
}