package com.ps.movie.feature.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ps.domain.modal.MovieResult
import com.ps.movie.R
import com.ps.movie.feature.MovieIntent
import com.ps.movie.feature.common.MovieAppBar
import com.ps.movie.feature.common.MovieBanner
import com.ps.movie.feature.list.viewModel.MovieListState
import com.ps.movie.feature.list.viewModel.MoviesListViewModel
import com.ps.movie.util.Constants
import com.ps.movie.util.TestTags

@Composable
fun MovieListScreen(
    viewModel: MoviesListViewModel = hiltViewModel(),
    onMovieClick: (MovieResult) -> Unit,
) {
    var movieList by remember { mutableStateOf(emptyList<MovieResult>()) }
    var message by remember { mutableStateOf("") }

    val lazyListState = rememberLazyListState()

    LaunchedEffect(key1 = Unit, block = {
        viewModel.initializeIntentHandler()
        viewModel.channel.send(MovieIntent.GetMovies)
    })

    Scaffold(
        topBar = {
            MovieAppBar(
                title = stringResource(id = R.string.movie_list),
                tagName = TestTags.MOVIE_LIST_TITLE,
                isBackEnabled = false,
                onBackClick = {},
            )
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues),
        ) {
            when (val state = viewModel.movieListState.collectAsStateWithLifecycle().value) {
                is MovieListState.OnMovieListSuccess -> {
                    movieList = state.response?.results ?: emptyList()
                    if (movieList.isEmpty()) {
                        Text(modifier = Modifier.padding(10.dp), text = message)
                    } else {
                        DisplayMovieList(results = movieList, onMovieClick = onMovieClick)
                    }
                }

                is MovieListState.OnMovieListFailure -> {
                    message = state.message
                }

                else -> Unit
            }
        }
    }
}

@Composable
fun DisplayMovieList(results: List<MovieResult>?, onMovieClick: (MovieResult) -> Unit) {
    LazyColumn(
        state = rememberLazyListState(),
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(horizontal = 10.dp)
            .testTag(TestTags.MOVIE_LIST),
    ) {
        items(results?.size ?: 0) { index ->
            results?.get(index)?.let { movie ->
                MovieBanner(
                    modifier = Modifier
                        .height(400.dp)
                        .padding(5.dp),
                    testTag = TestTags.MOVIE_LIST_ITEM_IMAGE + index,
                    imagePath = "${Constants.IMAGE_URL}${movie.posterPath}",
                ) {
                    onMovieClick(movie)
                }
            }
        }
    }
}
