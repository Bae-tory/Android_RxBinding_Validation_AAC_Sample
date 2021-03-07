package com.project.baetory_rx_validation.ui.signup

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.view.ViewStub
import android.view.WindowManager
import android.widget.Button
import androidx.activity.viewModels
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxCompoundButton
import com.project.baetory_rx_validation.R
import com.project.baetory_rx_validation.base.BaseActivity
import com.project.baetory_rx_validation.databinding.ActivitySignUpBinding
import com.project.baetory_rx_validation.ext.startActivityWithData
import com.project.baetory_rx_validation.ext.toast
import com.project.baetory_rx_validation.model.UiState
import com.project.baetory_rx_validation.ui.info.InfoActivity
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.*
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class SignUpActivity :
    BaseActivity<ActivitySignUpBinding, SignUpViewModel>(R.layout.activity_sign_up) {

    override val vm: SignUpViewModel by viewModels()
    private val currentCal: Calendar by lazy { Calendar.getInstance() }
    private var currentYear = currentCal.get(Calendar.YEAR)
    private var currentMonth = currentCal.get(Calendar.MONTH)
    private var currentDayOfMonth = currentCal.get(Calendar.DAY_OF_MONTH)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRxBinding()
    }

    private fun setRxBinding() {
        bind {
            // 비밀번호 *숨김 여부
            RxCompoundButton.checkedChanges(cbPwdVisible)
                .debounce(DEBOUNCE_TIME, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { checked ->
                    val inputType = if (checked) {
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                    } else {
                        InputType.TYPE_CLASS_TEXT
                    }
                    //비밀번호, 비밀번호 확인 View
                    etSignupPwd.inputType = inputType
                    etSignupPwdConfirm.inputType = inputType

                }.addDisposable()

            // 최대 날짜 설정
            val maxCal = Calendar.getInstance()
            maxCal.set(currentYear, currentMonth, currentDayOfMonth)
            RxView.touches(etSignupBirthday)// 생년월일 클릭
                .throttleFirst(THROTTLE_TIME, TimeUnit.MILLISECONDS)
                .subscribe {
                    // 날짜 선택 다이얼로그 생성
                    DatePickerDialog(
                        this@SignUpActivity, R.style.SignUp_DatePicker, DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                            currentYear = year
                            currentMonth = month
                            currentDayOfMonth = dayOfMonth
                            etSignupBirthday.setText(getString(R.string.picked_date, year, month + 1, dayOfMonth))
                        }, currentYear, currentMonth, currentDayOfMonth
                    ).apply {
                        // 날짜 선택 다이얼로그 보여주기
                        datePicker.maxDate = maxCal.timeInMillis
                        show()
                    }
                }.addDisposable()

            // 로딩 뷰 보여주기 or 숨기기
            // 로딩 View Stub
            val loadingView = loading_stub as ViewStub
            vm?.uiStateSubject
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe { result -> /*로딩중, 성공, 에러*/
                    when (result) {
                        is UiState.Loading -> {
                            loadingViewVisible(loadingView, btnEngage)
                        }
                        is UiState.Success -> {
                            loadingViewGone(loadingView, btnEngage)
                            toast(getString(R.string.result_msg, result.successCode, result.successMsg))
                            startActivityWithData(InfoActivity::class.java) {
                                putExtras(result.data)
                                finish()
                                overridePendingTransition(0, 0)
                            }
                        }
                        is UiState.Error -> {
                            loadingViewGone(loadingView, btnEngage)
                            toast(getString(R.string.result_msg, result.errorCode, result.errorMsg))
                        }
                    }
                }?.addDisposable()

            // 회원가입 버튼 On / Off
            vm?.signUpButtonSubject
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe { btnEngage.isEnabled = it }
                ?.addDisposable()
        }
    }

    private fun loadingViewGone(loadingView: ViewStub, btnEngage: Button) {
        loadingView.visibility = View.GONE
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        btnEngage.visibility = View.VISIBLE
    }

    private fun loadingViewVisible(loadingView: ViewStub, btnEngage: Button) {
        loadingView.visibility = View.VISIBLE
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
        btnEngage.visibility = View.GONE
    }

    companion object {
        const val DEBOUNCE_TIME = 200L
        const val THROTTLE_TIME = 200L
    }
}