package com.ps.movies.ui.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ps.domain.modal.MovieResult
import com.ps.movies.R
import com.ps.movies.ui.UiEvent
import com.ps.movies.ui.common.MovieAppBar
import com.ps.movies.ui.common.MovieBanner
import com.ps.movies.ui.list.viewModel.MovieListState
import com.ps.movies.ui.list.viewModel.MoviesListViewModel
import com.ps.movies.util.Constants
import com.ps.movies.util.TestTags

@Composable
fun MovieListScreen(
    viewModel: MoviesListViewModel = hiltViewModel(),
    onMovieClick: (Int) -> Unit,
) {
    LaunchedEffect(key1 = Unit, block = {
        viewModel.onEvent(UiEvent.InitState)
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
                is MovieListState.Loading -> {
                    Text(modifier = Modifier.padding(10.dp), text = Constants.MESSAGE_LOADING)
                }

                is MovieListState.OnMovieListSuccess -> {
                    state.response.results.let {
                        DisplayMovieList(
                            results = it,
                            onMovieClick = onMovieClick,
                        )
                    }
                }

                is MovieListState.OnMovieListFailure -> {
                    Text(modifier = Modifier.padding(10.dp), text = state.message)
                }
            }
        }
    }
}

@Composable
fun DisplayMovieList(results: List<MovieResult>, onMovieClick: (Int) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(horizontal = 10.dp)
            .testTag(TestTags.MOVIE_LIST),
    ) {
        itemsIndexed(results) { index, movie ->
            MovieBanner(
                modifier = Modifier
                    .height(400.dp)
                    .padding(5.dp),
                testTag = TestTags.MOVIE_LIST_ITEM_IMAGE + index,
                imagePath = "${Constants.IMAGE_URL}${movie.posterPath}",
            ) {
                onMovieClick(movie.id)
            }
        }
    }
}
