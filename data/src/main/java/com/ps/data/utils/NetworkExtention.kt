package com.ps.data.utils

import com.ps.domain.mapper.Mapper
import com.ps.domain.utils.NetworkResponse
import retrofit2.HttpException
import retrofit2.Response

suspend fun <R : Mapper<T>, T : Any> safeApiCall(
    apiCall: suspend () -> Response<R>,
    dataMapper: (R) -> T,
): NetworkResponse<T> {
    return try {
        val response = apiCall()
        return when {
            response.isSuccessful ->
                response.body()?.let {
                    NetworkResponse.Success(dataMapper(it))
                } ?: NetworkResponse.Error(errorMessage = Constants.EMPTY_BODY)

            else -> NetworkResponse.Error(errorMessage = response.message())
        }
    } catch (exception: HttpException) {
        NetworkResponse.Error(errorMessage = exception.localizedMessage ?: Constants.SERVER_ERROR)
    } catch (throwable: Throwable) {
        NetworkResponse.Exception(throwable = throwable)
    }
}
