package com.ps.movie.feature.details.viewModel

import com.ps.domain.modal.MovieDetailResponse

sealed interface MovieDetailState {
    data object Loading : MovieDetailState
    data class OnMovieDetailSuccess(val response: MovieDetailResponse) : MovieDetailState
    data class OnMovieDetailFailure(val message: String?) : MovieDetailState
}
