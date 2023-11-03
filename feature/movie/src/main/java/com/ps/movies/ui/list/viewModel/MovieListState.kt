package com.ps.movies.ui.list.viewModel

import com.ps.domain.modal.MovieListDomainModel

sealed interface MovieListState {
    data object Loading : MovieListState
    data class OnMovieListSuccess(val response: MovieListDomainModel) : MovieListState
    data class OnMovieListFailure(val message: String) : MovieListState
}
