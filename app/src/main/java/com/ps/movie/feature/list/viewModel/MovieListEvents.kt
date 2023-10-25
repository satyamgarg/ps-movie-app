package com.ps.movie.feature.list.viewModel

import com.ps.domain.modal.MovieListResponse

sealed class MovieListEvents {
    data object Loading : MovieListEvents()
    data class OnMovieListSuccess(val response: MovieListResponse?) : MovieListEvents()
    data class OnMovieListFailure(val message: String) : MovieListEvents()
}
