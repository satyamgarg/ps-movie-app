package com.ps.movie.feature.details.viewModel

import com.ps.domain.modal.MovieDetailResponse

sealed class MovieDetailEvent {
    data object Void : MovieDetailEvent()
    data object Loading : MovieDetailEvent()
    data class OnMovieDetailSuccess(val response: MovieDetailResponse?) : MovieDetailEvent()
    data class OnMovieDetailFailure(val message: String?) : MovieDetailEvent()
}
