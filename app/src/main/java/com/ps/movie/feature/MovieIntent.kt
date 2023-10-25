package com.ps.movie.feature

sealed class MovieIntent {
    data object GetMovies : MovieIntent()
    data class GetMovieDetails(val movieId: Int) : MovieIntent()
}
