package com.project.data.component

sealed class Result<out T> {
    data class OnSuccess<out T>(val data: T) : Result<T>()
    data class OnError(val exception: Exception) : Result<Nothing>()
}