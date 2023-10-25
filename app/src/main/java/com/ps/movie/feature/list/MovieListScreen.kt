package com.ps.movie.feature.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ps.domain.modal.MovieResult
import com.ps.movie.R
import com.ps.movie.feature.MovieIntent
import com.ps.movie.feature.common.MovieBanner
import com.ps.movie.feature.list.viewModel.MovieListEvents
import com.ps.movie.feature.list.viewModel.MoviesListViewModel
import com.ps.movie.util.Constants
import com.ps.movie.util.TestTags

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListScreen(
    viewModel: MoviesListViewModel = hiltViewModel(),
    onMovieClick: (MovieResult) -> Unit,
) {
    var movieList by remember { mutableStateOf(emptyList<MovieResult>()) }
    var message by remember { mutableStateOf("") }

    LaunchedEffect(key1 = Unit, block = {
        viewModel.channel.send(MovieIntent.GetMovies)
        viewModel.movieListEvent.collect { event ->
            when (event) {
                is MovieListEvents.OnMovieListSuccess -> {
                    movieList = event.response?.results ?: emptyList()
                }

                is MovieListEvents.OnMovieListFailure -> {
                    message = event.message
                }

                else -> Unit
            }
        }
    })

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.testTag(TestTags.MOVIE_LIST_TITLE),
                        text = stringResource(id = R.string.movie_list),
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black),
            )
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues),
        ) {
            if (movieList.isEmpty()) {
                Text(modifier = Modifier.padding(10.dp), text = message,)
            } else {
                DisplayMovieList(results = movieList, onMovieClick = onMovieClick)
            }
        }
    }
}

@Composable
fun DisplayMovieList(results: List<MovieResult>?, onMovieClick: (MovieResult) -> Unit) {
    LazyColumn(
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
