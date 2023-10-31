package com.ps.domain.usecase

import com.ps.domain.modal.MovieListResponse
import com.ps.domain.repository.MovieRepository
import com.ps.domain.utils.Constants
import com.ps.domain.utils.NetworkResponse
import javax.inject.Inject

open class MovieListUseCase @Inject constructor(private val movieRepository: MovieRepository) {
    suspend operator fun invoke(): NetworkResponse<MovieListResponse> {
        return when (val response = movieRepository.getMoviesList()) {
            is NetworkResponse.Success -> {
                return if (response.data.results.isEmpty()) {
                    NetworkResponse.Error(Constants.EMPTY_LIST)
                } else {
                    NetworkResponse.Success(response.data)
                }
            }
            is NetworkResponse.Error -> NetworkResponse.Error(response.errorMessage)
            is NetworkResponse.Exception -> NetworkResponse.Exception(response.throwable)
        }
    }
}
