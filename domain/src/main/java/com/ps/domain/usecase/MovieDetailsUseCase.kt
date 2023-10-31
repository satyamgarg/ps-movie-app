package com.ps.domain.usecase

import com.ps.domain.modal.MovieDetailResponse
import com.ps.domain.repository.MovieRepository
import com.ps.domain.utils.Constants
import com.ps.domain.utils.NetworkResponse
import java.net.UnknownHostException
import javax.inject.Inject

open class MovieDetailsUseCase @Inject constructor(private val movieRepository: MovieRepository) {
    suspend operator fun invoke(movieId: String): NetworkResponse<MovieDetailResponse> {
        return when (val response = movieRepository.getMovieDetails(movieId)) {
            is NetworkResponse.Success -> {
                return response.data?.let { NetworkResponse.Success(response.data) }
                    ?: kotlin.run { NetworkResponse.Error(Constants.EMPTY_LIST) }
            }

            is NetworkResponse.Error -> NetworkResponse.Error(
                response.errorMessage ?: Constants.UNKNOWN_ERROR,
            )

            is NetworkResponse.Exception -> NetworkResponse.Exception(
                response.exception ?: UnknownHostException(
                    Constants.API_ERROR,
                ),
            )
        }
    }
}
