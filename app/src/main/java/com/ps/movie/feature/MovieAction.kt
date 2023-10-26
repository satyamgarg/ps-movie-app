package com.ps.movie.feature

import com.ps.domain.modal.MovieDetailResponse

sealed interface MovieAction {
    data object GetMovieList : MovieAction
    data class GetMovieDetails(val movieId: Int) : MovieAction
    data class DisplayAvailableDetails(val movieDetailResponse: MovieDetailResponse) : MovieAction
    data object None : MovieAction
}
