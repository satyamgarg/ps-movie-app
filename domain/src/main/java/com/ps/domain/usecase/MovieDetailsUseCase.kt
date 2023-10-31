package com.ps.domain.usecase

import com.ps.domain.modal.MovieDetailResponse
import com.ps.domain.repository.MovieRepository
import com.ps.domain.utils.NetworkResponse
import javax.inject.Inject

open class MovieDetailsUseCase @Inject constructor(private val movieRepository: MovieRepository) {
    suspend operator fun invoke(movieId: String): NetworkResponse<MovieDetailResponse> {
        return when (val response = movieRepository.getMovieDetails(movieId)) {
            is NetworkResponse.Success -> {
                return NetworkResponse.Success(response.data)
            }

            is NetworkResponse.Error -> NetworkResponse.Error(
                response.errorMessage,
            )

            is NetworkResponse.Exception -> NetworkResponse.Exception(
                response.throwable,
            )
        }
    }
}
