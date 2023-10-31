package com.ps.domain.utils

sealed interface NetworkResponse<T> {
    data class Success<T>(val data: T) : NetworkResponse<T>
    data class Error<T>(val errorMessage: String) : NetworkResponse<T>
    data class Exception<T>(val throwable: Throwable) : NetworkResponse<T>
}
