package com.ps.domain.repository

import com.ps.domain.modal.MovieListDomainModel
import com.ps.domain.utils.Result

interface MovieListRepository {
    suspend fun getMoviesList(): Result<MovieListDomainModel>
}
