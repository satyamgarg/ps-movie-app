package com.ps.domain.usecase

import com.ps.domain.modal.MovieDetailsDomainModel
import com.ps.domain.repository.MovieDetailRepository
import com.ps.domain.utils.Result
import javax.inject.Inject

open class MovieDetailsUseCase @Inject constructor(private val movieDetailRepository: MovieDetailRepository) {
    suspend operator fun invoke(movieId: String): Result<MovieDetailsDomainModel> =
        movieDetailRepository.getMovieDetails(movieId)
}
