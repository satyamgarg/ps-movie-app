package com.ps.domain.utils

sealed interface Result<T> {
    data class Success<T>(val data: T) : Result<T>
    data class Error<T>(val errorMessage: String) : Result<T>
    data class Exception<T>(val throwable: Throwable) :
        Result<T>
}
