package com.ps.domain.repository

import com.ps.domain.modal.MovieDetailsDomainModel
import com.ps.domain.utils.Result

interface MovieDetailRepository {
    suspend fun getMovieDetails(movieId: String): Result<MovieDetailsDomainModel>
}
