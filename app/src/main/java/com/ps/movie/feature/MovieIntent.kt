package com.ps.movie.feature

import com.ps.domain.modal.MovieDetailResponse

sealed interface MovieIntent {
    data object GetMovies : MovieIntent
    data class GetMovieDetails(val movieId: Int) : MovieIntent
    data class DisplayAvailableDetails(val movieDetailResponse: MovieDetailResponse) : MovieIntent
}
