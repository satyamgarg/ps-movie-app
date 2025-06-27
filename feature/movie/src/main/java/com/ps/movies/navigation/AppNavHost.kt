package com.ps.movies.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ps.movies.ui.list.MovieListScreen
import com.ps.movies.util.Constants
import com.ps.movies.util.NavigationRoute.MOVIE_DETAIL
import com.ps.movies.util.NavigationRoute.MOVIE_LIST

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AppNav.MovieList.route,
    ) {
        composable(route = AppNav.MovieList.route) {
            MovieListScreen {
                navController.navigate(
                    AppNav.MovieDetail.route + "/$it",
                )
            }
        }

        composable(
            route = AppNav.MovieDetail.route + "/{${Constants.MOVIE_ID}}",
        ) {
            //Navigation
        }
    }
}

sealed class AppNav(val route: String) {
    data object MovieList : AppNav(route = MOVIE_LIST)
    data object MovieDetail : AppNav(route = MOVIE_DETAIL)
}
