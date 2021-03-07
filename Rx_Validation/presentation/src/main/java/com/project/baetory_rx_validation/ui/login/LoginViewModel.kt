package com.project.baetory_rx_validation.ui.login

import android.os.Handler
import androidx.core.os.bundleOf
import androidx.hilt.lifecycle.ViewModelInject
import com.project.local.source.LocalDataSource
import com.project.baetory_rx_validation.R
import com.project.baetory_rx_validation.base.BaseViewModel
import com.project.baetory_rx_validation.component.ResourceProvider
import com.project.baetory_rx_validation.model.UiState
import com.project.baetory_rx_validation.model.fromLocal
import io.reactivex.subjects.PublishSubject
import java.net.HttpURLConnection.*

class LoginViewModel @ViewModelInject constructor(
    private val resourceProvider: ResourceProvider,
    private val localDataSource: LocalDataSource
) : BaseViewModel() {

    val uiStateSubject = PublishSubject.create<UiState>()

    fun loginButtonClicked(inputUserData: Pair<String, String>) {
        val inputUserEmail = inputUserData.first
        val inputUserPassword = inputUserData.second
        uiStateSubject
            .onNext(UiState.Loading)

        Handler().postDelayed({//2초 지연

            localDataSource
                .getUserByEmail(inputUserEmail)
                .subscribe({ savedUser ->
                    val user = savedUser.fromLocal()
                    if (user.password == inputUserPassword) { // 비밀번호가 맞다면
                        val userData = bundleOf(
                            Pair(EMAIL, user.email),
                            Pair(PASSWORD, user.password),
                            Pair(NICKNAME, user.nickname),
                            Pair(BIRTHDAY, user.birthday),
                            Pair(GENDER, user.gender),
                            Pair(AGREE_TERM, user.agreeTerm),
                            Pair(AGREE_MARKETING, user.agreeMarket)
                        )
                        uiStateSubject
                            .onNext(UiState.Success(HTTP_OK, resourceProvider.getString(R.string.success_login), userData))
                    } else {// 비밀번호가 틀리다면
                        uiStateSubject
                            .onNext(UiState.Error(HTTP_NOT_FOUND, resourceProvider.getString(R.string.fail_password)))
                        return@subscribe
                    }
                }, {
                    uiStateSubject
                        .onNext(UiState.Error(HTTP_BAD_REQUEST, resourceProvider.getString(R.string.fail_get_user_info)))
                    return@subscribe
                }, {
                    uiStateSubject
                        .onNext(UiState.Error(HTTP_NOT_FOUND, resourceProvider.getString(R.string.fail_no_account)))
                    return@subscribe
                }
                ).addDisposable()


        }, DELAY_TIME)
    }

    companion object {

        const val DELAY_TIME = 2000L

        // 유저 데이터
        const val EMAIL = "Email"
        const val PASSWORD = "Password"
        const val NICKNAME = "Nickname"
        const val BIRTHDAY = "Birthday"
        const val GENDER = "Gender"
        const val AGREE_TERM = "Term"
        const val AGREE_MARKETING = "Marketing"

    }
}