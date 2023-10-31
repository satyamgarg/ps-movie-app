package com.ps.domain.repository

import com.ps.domain.modal.MovieDetailResponse
import com.ps.domain.modal.MovieListResponse
import com.ps.domain.utils.NetworkResponse

interface MovieRepository {
    suspend fun getMoviesList(): NetworkResponse<MovieListResponse>
    suspend fun getMovieDetails(movieId: String): NetworkResponse<MovieDetailResponse>
}
