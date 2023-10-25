package com.ps.movie.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ps.domain.modal.MovieResult
import com.ps.movie.feature.details.MovieDetailScreen
import com.ps.movie.feature.list.MovieListScreen
import com.ps.movie.util.Constants
import com.ps.movie.util.Constants.OPR_AND
import com.ps.movie.util.Constants.OPR_QUESTION
import com.ps.movie.util.MoshiParser

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AppNav.MovieList.route,
    ) {
        composable(route = AppNav.MovieList.route) {
            MovieListScreen {
                navController.navigate(
                    AppNav.MovieDetail.route + OPR_QUESTION + getArgs(
                        Constants.MOVIE_DETAILS to Uri.encode(
                            MoshiParser().toJson(it, MovieResult::class.java),
                        ),
                    ),
                )
            }
        }

        composable(
            route = AppNav.MovieDetail.route + getArgsString(
                Constants.MOVIE_DETAILS,
            ),
            arguments = listOf(
                navArgument(Constants.MOVIE_DETAILS) {
                    type = NavType.StringType
                },
            ),
        ) {
            MovieDetailScreen(
                movieObj = Uri.decode(it.arguments?.getString(Constants.MOVIE_DETAILS))
                    .orEmpty(),
            ) {
                navController.navigateUp()
            }
        }
    }
}

private fun getArgsString(vararg argKey: String) =
    OPR_QUESTION + argKey.joinToString(separator = OPR_AND) {
        "$it={$it}"
    }

private fun getArgs(vararg args: Pair<String, Any>): String {
    return args.joinToString(OPR_AND) {
        "${it.first}=${it.second}"
    }
}
