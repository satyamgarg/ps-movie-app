package com.ps.data.repository

import com.ps.data.mapper.MovieListDataToDomainMapper
import com.ps.data.remote.MovieService
import com.ps.data.utils.safeApiCall
import com.ps.domain.modal.MovieListDomainModel
import com.ps.domain.repository.MovieListRepository
import com.ps.domain.utils.Result
import javax.inject.Inject

class MovieListRepositoryImpl @Inject constructor(
    private val movieService: MovieService,
    private val movieListMapper: MovieListDataToDomainMapper,
) :
    MovieListRepository {
    override suspend fun getMoviesList(): Result<MovieListDomainModel> =
        safeApiCall(
            apiCall = {
                movieService.getMoviesList()
            },
            dataMapper = {
                movieListMapper(it)
            },
        )
}
