package com.project.baetory_rx_validation.ui.signup

import android.os.Handler
import android.util.Patterns
import androidx.core.os.bundleOf
import androidx.hilt.lifecycle.ViewModelInject
import com.project.local.source.LocalDataSource
import com.project.baetory_rx_validation.R
import com.project.baetory_rx_validation.base.BaseViewModel
import com.project.baetory_rx_validation.component.Gender
import com.project.baetory_rx_validation.component.ResourceProvider
import com.project.baetory_rx_validation.model.UiState
import com.project.baetory_rx_validation.model.User
import com.project.baetory_rx_validation.model.fromLocal
import com.project.baetory_rx_validation.model.toLocal
import io.reactivex.Observable
import io.reactivex.functions.Function6
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import java.net.HttpURLConnection.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

class SignUpViewModel @ViewModelInject constructor(
    private val resourceProvider: ResourceProvider,
    private val localDataSource: LocalDataSource
) : BaseViewModel() {
    // 이메일
    var email
        get() = emailSubject.value ?: EMPTY_STRING
        set(value) {
            emailSubject.onNext(value)
        }

    // 비밀번호
    var password
        get() = pwdSubject.value ?: EMPTY_STRING
        set(value) {
            pwdSubject.onNext(value)
        }

    // 비밀번호 확인
    var confirmPassword
        get() = pwdConfirmSubject.value ?: EMPTY_STRING
        set(value) {
            pwdConfirmSubject.onNext(value)
        }

    // 별명
    var nickname
        get() = nicknameSubject.value ?: EMPTY_STRING
        set(value) {
            nicknameSubject.onNext(value)
        }

    // 생일
    var birthday
        get() = birthdaySubject.value ?: EMPTY_STRING
        set(value) {
            birthdaySubject.onNext(value)
        }

    // 약관
    var agreeTerm
        get() = agreeTermSubject.value ?: false
        set(value) {
            agreeTermSubject.onNext(value)
        }

    // 마케팅
    var agreeMarketing
        get() = agreeMarketingSubject.value ?: false
        set(value) {
            agreeMarketingSubject.onNext(value)
        }

    // 성별 선택 안하면 null, 한다면 Enum 값
    private var genderString: String? = null
    var gender: Gender? = null
        set(value) {
            genderString = when (value) {
                Gender.Man -> resourceProvider.getString(R.string.gender_man)
                Gender.Woman -> resourceProvider.getString(R.string.gender_woman)
                Gender.NonSelected -> resourceProvider.getString(R.string.gender_non_selected)
                else -> throw IllegalArgumentException(resourceProvider.getString(R.string.gender_error))
            }
            field = value
        }

    // Subject : 이메일, 비밀번호, 닉네임, 생일, 성별
    private val emailSubject = BehaviorSubject.createDefault(EMPTY_STRING)
    private val pwdSubject = BehaviorSubject.createDefault(EMPTY_STRING)
    private val pwdConfirmSubject = BehaviorSubject.createDefault(EMPTY_STRING)
    private val nicknameSubject = BehaviorSubject.createDefault(EMPTY_STRING)
    private val birthdaySubject = BehaviorSubject.createDefault(EMPTY_STRING)

    // Subject : 약관, 마케팅, 회원가입 동의
    private val agreeTermSubject = BehaviorSubject.createDefault(false)
    private val agreeMarketingSubject = BehaviorSubject.createDefault(false)
    val signUpButtonSubject = PublishSubject.create<Boolean>()

    // Subject : UI 관련
    val uiStateSubject = PublishSubject.create<UiState>()

    init {
        // 회원가입 Validation
        Observable.combineLatest(
            emailValidate(),// 이메일
            pwdValidate(),// 비밀번호
            pwdConfirmValidate(), // 비밀번호 확인
            nicknameValidate(),// 닉네임
            birthdayValidate(),// 생일
            agreeTermValidate(),// 약관 동의
            Function6 { emailValid: Boolean, pwdValid: Boolean, pwdConfirmValid: Boolean, nicknameValid: Boolean, birthdayValid: Boolean, agreeTermValid: Boolean ->
                emailValid && pwdValid && pwdConfirmValid && nicknameValid && birthdayValid && agreeTermValid && (gender != null)
            }
        ).subscribe(signUpButtonSubject::onNext)
            .addDisposable()
    }


    // 이메일 조건 판별
    private fun emailValidate(): Observable<Boolean> =
        emailSubject.map { Patterns.EMAIL_ADDRESS.matcher(it).matches() }

    // 비밀번호 조건 판별
    private fun pwdValidate(): Observable<Boolean> =
        pwdSubject
            .debounce(DEBOUNCE_TIME, TimeUnit.MILLISECONDS)
            .map { Pattern.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,20}$", it) }


    // 비밀번호 확인 조건 판별
    private fun pwdConfirmValidate(): Observable<Boolean> =
        pwdConfirmSubject
            .debounce(DEBOUNCE_TIME, TimeUnit.MILLISECONDS)
            .map { confirmPassword -> password == confirmPassword }

    // 별명 조건 판별
    private fun nicknameValidate(): Observable<Boolean> =
        nicknameSubject
            .debounce(DEBOUNCE_TIME, TimeUnit.MILLISECONDS)
            .map { nickname -> nickname.count() in 8..30 }

    // 생일 입력 여부 판별
    private fun birthdayValidate(): Observable<Boolean> =
        birthdaySubject
            .filter(String::isNotEmpty)
            .map { birthday ->
                SimpleDateFormat(BIRTHDAY_FORMAT, Locale.KOREA).apply {
                    isLenient = false
                    parse(birthday)
                }
                true
            }.onErrorReturn { false }

    // 약관 동의 여부 판별
    private fun agreeTermValidate(): Observable<Boolean> =
        agreeTermSubject
            .debounce(DEBOUNCE_TIME, TimeUnit.MILLISECONDS)

    fun btnSignUpButtonClicked() {
        /* 400,401 순으로 검증하고 완료 시 200OK*/
        uiStateSubject
            .onNext(UiState.Loading)// 서버 통신 시작
        Handler().postDelayed({//2초 지연
            // 1. if 400 HTTP_BAD_REQUEST (만 14세)
            val sdf = SimpleDateFormat(BIRTHDAY_FORMAT, Locale.KOREA)
            val standardCal = Calendar.getInstance()
            standardCal.set(standardCal.get(Calendar.YEAR) - 14, standardCal.get(Calendar.MONTH), standardCal.get(Calendar.DAY_OF_MONTH))// 만 14세 조건에 맞춘 날짜
            val standardTime = sdf.format(standardCal.time)

            val standardDate = sdf.parse(standardTime)
            val birthDate = sdf.parse(birthday)

            if (birthDate!!.after(standardDate)) {
                uiStateSubject// 서버 통신 종료 (회원가입 실패)
                    .onNext(UiState.Error(HTTP_BAD_REQUEST, resourceProvider.getString(R.string.http_code_400)))
                return@postDelayed
            }

            // 2. if 401 HTTP_UNAUTHORIZED (이미 가입된 계정)
            localDataSource
                .getUserByEmail(email)
                .subscribe({ savedUser ->//완료
                    val user = savedUser.fromLocal()
                    if (user.email == this.email) {
                        uiStateSubject// 서버 통신 종료 (이미 가입한 계정)
                            .onNext(UiState.Error(HTTP_UNAUTHORIZED, resourceProvider.getString(R.string.http_code_401)))
                        return@subscribe
                    } else {
                        insertUser()
                    }
                }, {
                    uiStateSubject
                        .onNext(UiState.Error(HTTP_BAD_REQUEST, resourceProvider.getString(R.string.fail_get_user_info)))
                    return@subscribe
                }, {
                    insertUser()
                }).addDisposable()
        }, DELAY_TIME)
    }

    private fun insertUser() {
        localDataSource
            .insertUser(User(System.currentTimeMillis(), email, password, nickname, genderString, birthday, agreeTerm, agreeMarketing).toLocal())
            .subscribe({
                // Success 200 HTTP_OK (회원 가입 완료)
                val userData = bundleOf(
                    Pair(EMAIL, email),
                    Pair(PASSWORD, password),
                    Pair(NICKNAME, nickname),
                    Pair(BIRTHDAY, birthday),
                    Pair(GENDER, genderString),
                    Pair(AGREE_TERM, agreeTerm),
                    Pair(AGREE_MARKETING, agreeMarketing)
                )
                uiStateSubject// 서버 통신 종료 (회원가입 성공)
                    .onNext(UiState.Success(HTTP_OK, resourceProvider.getString(R.string.http_code_200), userData))
            }, {
                uiStateSubject// 유저 저장 실패
                    .onNext(UiState.Error(SAVE_USER_ERROR, resourceProvider.getString(R.string.fail_save_user_data)))
            }).addDisposable()
    }

    companion object {
        const val DEBOUNCE_TIME = 300L
        const val EMPTY_STRING = ""
        const val DELAY_TIME = 2000L
        const val SAVE_USER_ERROR = 111

        // 입력필드 데이터 키
        const val EMAIL = "Email"
        const val PASSWORD = "Password"
        const val NICKNAME = "Nickname"
        const val BIRTHDAY = "Birthday"
        const val GENDER = "Gender"
        const val AGREE_TERM = "Term"
        const val AGREE_MARKETING = "Marketing"

        // 날짜 포맷
        const val BIRTHDAY_FORMAT = "yyyy-MM-dd"
    }
}