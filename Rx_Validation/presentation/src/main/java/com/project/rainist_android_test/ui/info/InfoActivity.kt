package com.project.rainist_android_test.ui.info

import android.os.Bundle
import androidx.activity.viewModels
import com.project.rainist_android_test.R
import com.project.rainist_android_test.base.BaseActivity
import com.project.rainist_android_test.databinding.ActivityInfoBinding
import com.project.rainist_android_test.ui.signup.SignUpViewModel.Companion.AGREE_MARKETING
import com.project.rainist_android_test.ui.signup.SignUpViewModel.Companion.AGREE_TERM
import com.project.rainist_android_test.ui.signup.SignUpViewModel.Companion.BIRTHDAY
import com.project.rainist_android_test.ui.signup.SignUpViewModel.Companion.EMAIL
import com.project.rainist_android_test.ui.signup.SignUpViewModel.Companion.GENDER
import com.project.rainist_android_test.ui.signup.SignUpViewModel.Companion.NICKNAME
import com.project.rainist_android_test.ui.signup.SignUpViewModel.Companion.PASSWORD
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InfoActivity : BaseActivity<ActivityInfoBinding, InfoViewModel>(R.layout.activity_info) {

    override val vm: InfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showLoginResult()
    }

    private fun showLoginResult() {
        bind {
            intent?.extras?.let {
                // 가입된 유저 정보 받기
                val userEmail = it.getString(EMAIL)
                val userPwd = it.getString(PASSWORD)
                val userNickName = it.getString(NICKNAME)
                val userBirth = it.getString(BIRTHDAY)
                val userGender = it.getString(GENDER)
                val userAgreeTerm = it.getBoolean(AGREE_TERM)
                val userAgreeMarketing = it.getBoolean(AGREE_MARKETING)

                tvEmail.text = userEmail
                tvPwd.text = userPwd
                tvNickname.text = userNickName
                tvBirth.text = userBirth
                tvGender.text = userGender

                val termAgreeText = if (userAgreeTerm) getString(R.string.agree) else getString(R.string.disagree)
                val marketAgreeText = if (userAgreeMarketing) getString(R.string.agree) else getString(R.string.disagree)

                tvAgreeTerm.text = when (userAgreeTerm) {
                    true -> getString(R.string.result_agree_term, termAgreeText)
                    false -> getString(R.string.result_agree_term, termAgreeText)
                }
                tvAgreeMarketing.text = when (userAgreeMarketing) {
                    true -> getString(R.string.result_agree_marketing, marketAgreeText)
                    false -> getString(R.string.result_agree_marketing, marketAgreeText)
                }
            }
        }
    }
}