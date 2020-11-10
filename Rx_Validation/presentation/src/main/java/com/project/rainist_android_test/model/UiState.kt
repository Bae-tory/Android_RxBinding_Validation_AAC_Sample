package com.project.rainist_android_test.model

import android.os.Bundle

sealed class UiState {

    object Loading : UiState()

    data class Success(
        val successCode: Int,
        val successMsg: String,
        val data: Bundle
    ) : UiState()

    data class Error(
        val errorCode: Int,
        val errorMsg: String
    ) : UiState()
}