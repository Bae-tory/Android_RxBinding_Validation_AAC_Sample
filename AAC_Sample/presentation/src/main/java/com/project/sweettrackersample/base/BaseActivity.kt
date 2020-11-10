package com.project.sweettrackersample.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.project.sweettrackersample.BR
import com.project.sweettrackersample.ext.toast


abstract class BaseActivity<B : ViewDataBinding, VM : BaseViewModel>(
    @LayoutRes private val layoutResId: Int
) : AppCompatActivity() {

    protected abstract val vm: VM
    protected lateinit var binding: B
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@BaseActivity, layoutResId)
        bind {
            lifecycleOwner = this@BaseActivity
            setVariable(BR.vm, vm)
        }
        observeMsg()
    }

    private fun observeMsg() {
        vm.errorMsg.observe(this@BaseActivity, Observer {
            toast(it)
        })

        vm.msg.observe(this@BaseActivity, Observer {
            toast(it)
        })
    }

    protected fun bind(action: B.() -> Unit) {
        binding.run(action)
    }

    override fun onDestroy() {
        vm.unBindViewModel()
        super.onDestroy()
    }
}