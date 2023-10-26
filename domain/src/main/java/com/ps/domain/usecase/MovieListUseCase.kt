package com.ps.domain.usecase

import com.ps.domain.modal.MovieListResponse
import com.ps.domain.repository.MovieRepository
import com.ps.domain.utils.NetworkResponse
import javax.inject.Inject

open class MovieListUseCase @Inject constructor(private val movieRepository: MovieRepository) {
    suspend fun getMoviesList(): NetworkResponse<MovieListResponse> {
        return movieRepository.getMoviesList()
    }
}
