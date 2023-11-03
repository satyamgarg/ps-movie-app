package com.ps.movies.navigation

import com.ps.movies.util.NavigationRoute.MOVIE_DETAIL
import com.ps.movies.util.NavigationRoute.MOVIE_LIST

sealed class AppNav(val route: String) {
    data object MovieList : AppNav(route = MOVIE_LIST)
    data object MovieDetail : AppNav(route = MOVIE_DETAIL)
}
