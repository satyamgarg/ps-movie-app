package com.ps.movies.ui.list.viewModel

import com.ps.domain.modal.MovieListResponse

sealed interface MovieListState {
    data object Loading : MovieListState
    data class OnMovieListSuccess(val response: MovieListResponse) : MovieListState
    data class OnMovieListFailure(val message: String) : MovieListState
}
