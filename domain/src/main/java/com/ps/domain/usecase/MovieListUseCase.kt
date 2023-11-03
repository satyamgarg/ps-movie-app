package com.ps.domain.usecase

import com.ps.domain.modal.MovieListDomainModel
import com.ps.domain.repository.MovieListRepository
import com.ps.domain.utils.Result
import javax.inject.Inject

open class MovieListUseCase @Inject constructor(private val movieListRepository: MovieListRepository) {
    suspend operator fun invoke(): Result<MovieListDomainModel> =
        movieListRepository.getMoviesList()
}
