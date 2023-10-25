package com.ps.domain.usecase

import com.ps.domain.modal.MovieListResponse
import com.ps.domain.repository.MovieRepository
import com.ps.domain.utils.Constants
import com.ps.domain.utils.NetworkResponse
import javax.inject.Inject

open class MovieListUseCase @Inject constructor(private val movieRepository: MovieRepository) {
    suspend fun getMoviesList(): NetworkResponse<MovieListResponse> {
        return when (val response = movieRepository.getMoviesList()) {
            is NetworkResponse.Success -> handleMovieListSuccessResponse(response.data)
            is NetworkResponse.Error -> handleMovieListError(response.errorMessage.orEmpty())
            is NetworkResponse.Exception -> handleMovieListError(response.exception?.localizedMessage.orEmpty())
        }
    }

    private fun handleMovieListSuccessResponse(data: MovieListResponse?): NetworkResponse<MovieListResponse> {
        return if (data == null || data.results?.isEmpty() == true) {
            NetworkResponse.Error(Constants.EMPTY_LIST)
        } else {
            NetworkResponse.Success(data)
        }
    }

    private fun handleMovieListError(data: String): NetworkResponse<MovieListResponse> {
        return NetworkResponse.Error(data)
    }
}

