package com.project.rainist_android_test.ui.login

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.ViewStub
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import com.jakewharton.rxbinding2.view.RxView
import com.project.rainist_android_test.R
import com.project.rainist_android_test.base.BaseActivity
import com.project.rainist_android_test.databinding.ActivityLoginBinding
import com.project.rainist_android_test.ext.hideKeyboard
import com.project.rainist_android_test.ext.startActivity
import com.project.rainist_android_test.ext.startActivityWithData
import com.project.rainist_android_test.ext.toast
import com.project.rainist_android_test.model.UiState
import com.project.rainist_android_test.ui.info.InfoActivity
import com.project.rainist_android_test.ui.signup.SignUpActivity
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.combineLatest
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(R.layout.activity_login) {

    override val vm: LoginViewModel by viewModels()
    private val emailSubject = BehaviorSubject.createDefault(false)
    private val pwdSubject = BehaviorSubject.createDefault(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRxBinding()
    }

    private fun setRxBinding() {
        bind {

            // 이메일
            etLoginEmail.doOnTextChanged { char, _, _, _ ->
                char?.let {
                    if (Patterns.EMAIL_ADDRESS.matcher(it).matches()) {
                        emailSubject.onNext(true)
                    } else {
                        emailSubject.onNext(false)
                    }
                }
            }
            // 패스워드
            etLoginPwd.doOnTextChanged { char, _, _, _ ->
                char?.let {
                    if (Pattern.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,20}$", it)) {
                        pwdSubject.onNext(true)
                    } else {
                        pwdSubject.onNext(false)
                    }
                }
            }

            // 로그인 버튼 활성화
            listOf(emailSubject, pwdSubject)
                .combineLatest {
                    it.fold(true, { t1, t2 -> t1 && t2 })
                }.subscribe {
                    btnLogin.isEnabled = it
                }.addDisposable()

            // 로그인 버튼 클릭
            RxView.clicks(btnLogin)
                .debounce(DEBOUNCE_TIME, TimeUnit.MILLISECONDS)
                .filter { btnLogin.isEnabled }
                .observeOn(AndroidSchedulers.mainThread())
                .map { Pair(etLoginEmail.text.toString(), etLoginPwd.text.toString()) }
                .subscribe { vm?.loginButtonClicked(it) }
                .addDisposable()

            // 회원 가입 버튼
            RxView.clicks(tvSignup)
                .throttleFirst(THROTTLE_TIME, TimeUnit.MILLISECONDS)
                .subscribe {
                    startActivity(SignUpActivity::class.java)
                    finish()
                    overridePendingTransition(0, 0)
                }.addDisposable()


            // 로딩 프로그레스
            val loadingView = loading_stub as ViewStub
            vm?.uiStateSubject
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe { result -> /*로딩중, 성공, 에러*/
                    when (result) {
                        is UiState.Loading -> {
                            loadingViewVisible(loadingView)
                            hideKeyboard(this@LoginActivity.currentFocus ?: return@subscribe)
                        }
                        is UiState.Success -> {
                            loadingViewGone(loadingView)
                            toast(getString(R.string.result_msg, result.successCode, result.successMsg))
                            startActivityWithData(InfoActivity::class.java) {
                                putExtras(result.data)
                                finish()
                                overridePendingTransition(0, 0)
                            }
                        }
                        is UiState.Error -> {
                            loadingViewGone(loadingView)
                            toast(getString(R.string.result_msg, result.errorCode, result.errorMsg))
                        }
                    }
                }?.addDisposable()
        }
    }

    private fun loadingViewGone(loadingView: ViewStub) {
        loadingView.visibility = View.GONE
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    private fun loadingViewVisible(loadingView: ViewStub) {
        loadingView.visibility = View.VISIBLE
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }

    companion object {

        const val DEBOUNCE_TIME = 500L
        const val THROTTLE_TIME = 300L

    }
}