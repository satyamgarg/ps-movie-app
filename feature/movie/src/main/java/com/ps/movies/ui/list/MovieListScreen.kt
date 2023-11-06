package com.ps.movies.ui.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ps.domain.modal.MovieResultDomainModel
import com.ps.movies.R
import com.ps.movies.theme.LocalDimension
import com.ps.movies.ui.UiEvent
import com.ps.movies.ui.component.MovieAppBar
import com.ps.movies.ui.component.MovieBanner
import com.ps.movies.ui.list.viewModel.MovieListState
import com.ps.movies.ui.list.viewModel.MoviesListViewModel
import com.ps.movies.util.Constants

@Composable
fun MovieListScreen(
    viewModel: MoviesListViewModel = hiltViewModel(),
    onMovieClick: (Int) -> Unit,
) {
    val localDims = LocalDimension.current
    LaunchedEffect(key1 = Unit, block = {
        viewModel.onEvent(UiEvent.InitState)
    })

    Scaffold(
        topBar = {
            MovieAppBar(
                title = stringResource(id = R.string.movie_list),
                isBackEnabled = false,
            )
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues),
        ) {
            when (val state = viewModel.movieListState.collectAsStateWithLifecycle().value) {
                is MovieListState.Loading -> {
                    Text(
                        modifier = Modifier.padding(localDims.padding10),
                        text = Constants.MESSAGE_LOADING,
                    )
                }

                is MovieListState.OnMovieListSuccess -> {
                    DisplayMovieList(
                        results = state.response.results,
                        onMovieClick = onMovieClick,
                    )
                }

                is MovieListState.OnMovieListFailure -> {
                    Text(modifier = Modifier.padding(localDims.padding10), text = state.message)
                }
            }
        }
    }
}

@Composable
fun DisplayMovieList(results: List<MovieResultDomainModel>, onMovieClick: (Int) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
    ) {
        items(results) { movie ->
            MovieBanner(
                modifier = Modifier,
                imagePath = "${Constants.IMAGE_URL}${movie.posterPath}",
            ) {
                onMovieClick(movie.id)
            }
        }
    }
}
