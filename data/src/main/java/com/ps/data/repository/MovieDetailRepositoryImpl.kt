package com.ps.data.repository

import com.ps.data.mapper.MovieDetailsDataToDomainMapper
import com.ps.data.remote.MovieService
import com.ps.data.utils.safeApiCall
import com.ps.domain.modal.MovieDetailsDomainModel
import com.ps.domain.repository.MovieDetailRepository
import com.ps.domain.utils.Result
import javax.inject.Inject

class MovieDetailRepositoryImpl @Inject constructor(
    private val movieService: MovieService,
    private val movieDetailsMapper: MovieDetailsDataToDomainMapper,
) :
    MovieDetailRepository {

    override suspend fun getMovieDetails(movieId: String): Result<MovieDetailsDomainModel> =
        safeApiCall(
            apiCall = {
                movieService.getMovieDetails(movieId)
            },
            dataMapper = {
                movieDetailsMapper(it)
            },
        )
}
