package com.ps.movie.navigation

sealed class AppNav(val route: String) {
    data object MovieList : AppNav(route = "movie_list")
    data object MovieDetail : AppNav(route = "movie_detail")
}
