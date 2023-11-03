package com.ps.data.utils

import com.ps.domain.utils.Result
import retrofit2.HttpException
import retrofit2.Response

suspend fun <R, T> safeApiCall(
    apiCall: suspend () -> Response<R>,
    dataMapper: (R) -> T,
): Result<T> {
    return try {
        val response = apiCall()
        return when {
            response.isSuccessful ->
                response.body()?.let {
                    Result.Success(data = dataMapper(it))
                } ?: Result.Error(errorMessage = Constants.EMPTY_BODY)

            else -> Result.Error(errorMessage = response.message())
        }
    } catch (exception: HttpException) {
        Result.Error(errorMessage = exception.localizedMessage ?: Constants.SERVER_ERROR)
    } catch (throwable: Throwable) {
        Result.Exception(throwable = throwable)
    }
}
