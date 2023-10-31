package com.ps.movie.feature.list

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
    onMovieClick: (Int) -> Unit,
) {
    val mutableMovieList = remember { mutableStateOf<List<MovieResult>>(emptyList()) }

    LaunchedEffect(key1 = Unit, block = {
        if (mutableMovieList.value.isEmpty()) {
            viewModel.initializeIntentHandler()
            viewModel.channel.send(MovieIntent.GetMovies)
        }
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
                    val movieList = state.response?.results
                    movieList?.let {
                        mutableMovieList.value = it
                        DisplayMovieList(
                            results = it,
                            onMovieClick = onMovieClick,
                        )
                    }
                }

                is MovieListState.OnMovieListFailure -> {
                    Text(modifier = Modifier.padding(10.dp), text = state.message)
                }

                else -> Unit
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
                movie.id?.let(onMovieClick)
            }
        }
    }
}
