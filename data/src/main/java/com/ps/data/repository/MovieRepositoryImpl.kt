package com.ps.data.repository

import com.ps.data.remote.MovieService
import com.ps.data.utils.safeApiCall
import com.ps.domain.modal.MovieDetailResponse
import com.ps.domain.modal.MovieListResponse
import com.ps.domain.repository.MovieRepository
import com.ps.domain.utils.NetworkResponse
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val movieService: MovieService) :
    MovieRepository {
    override suspend fun getMoviesList(): NetworkResponse<MovieListResponse> =
        safeApiCall(
            apiCall = {
                movieService.getMoviesList()
            },
            dataMapper = {
                it.mapToDomain()
            },
        )

    override suspend fun getMovieDetails(movieId: Int): NetworkResponse<MovieDetailResponse> =
        safeApiCall(
            apiCall = {
                movieService.getMovieDetails(movieId)
            },
            dataMapper = {
                it.mapToDomain()
            },
        )
}
