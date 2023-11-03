package com.ps.movies.ui.details.viewModel

import com.ps.domain.modal.MovieDetailsDomainModel

sealed interface MovieDetailState {
    data object Loading : MovieDetailState
    data class OnMovieDetailSuccess(val response: MovieDetailsDomainModel) : MovieDetailState
    data class OnMovieDetailFailure(val message: String?) : MovieDetailState
}
