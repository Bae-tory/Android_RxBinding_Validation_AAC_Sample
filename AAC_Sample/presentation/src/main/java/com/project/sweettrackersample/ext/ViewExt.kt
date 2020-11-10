package com.project.sweettrackersample.ext

import android.animation.ObjectAnimator
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.databinding.BindingAdapter
import com.project.sweettrackersample.R
import com.project.sweettrackersample.component.ThrottleFirstClickListener
import kotlinx.coroutines.*

@BindingAdapter("onThrottleFirstClick")
fun View.setOnThrottleClickListener(listener: View.OnClickListener) {
    //1. RxJava - 별도의 class 구현
    setOnClickListener(ThrottleFirstClickListener {
        it.run(listener::onClick)
    })
    //2. Coroutine
    //3. RxJava - 뷰모델 subject를 이용하는 방법
    //4. RxJava - RxBidning 이용하는 방법
}

fun <T> throttleFirst(
    skipMs: Long = 500L,
    coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main),
    invokedFunc: (T) -> Unit

): (T) -> Unit {
    var throttleJob: Job? = null
    return { param: T ->
        if (throttleJob?.isCompleted != false) {
            throttleJob = coroutineScope.launch {
                invokedFunc(param)
                delay(skipMs)
            }
        }
    }
}

@BindingAdapter("copyText")
fun View.setClipBoardCopy(copyText: String?) {
    setOnLongClickListener {
        copyText?.let {
            val clipBoard = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            clipBoard.setPrimaryClip(ClipData.newPlainText("label", it))
            context.toast(context.getString(R.string.copy_clip_board) + "\t${copyText}")
        }
        false
    }
}

@BindingAdapter("setVisibleAnim")
fun View.setVisibleAnim(isVisible: Boolean) {
    if (isVisible) {
        visibility = View.VISIBLE
    } else {
        ObjectAnimator.ofFloat(this, "alpha", 1f, 0f).apply {
            interpolator = AccelerateDecelerateInterpolator()
            duration = 200L
        }.start()
    }
}