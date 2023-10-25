package com.ps.domain.usecase

import com.ps.domain.modal.MovieDetailResponse
import com.ps.domain.repository.MovieRepository
import com.ps.domain.utils.NetworkResponse
import javax.inject.Inject

open class MovieDetailsUseCase @Inject constructor(private val movieRepository: MovieRepository) {
    suspend fun getMovieDetails(movieId: Int): NetworkResponse<MovieDetailResponse> {
        return movieRepository.getMovieDetails(movieId = movieId)
    }
}

