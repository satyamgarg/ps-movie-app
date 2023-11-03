package com.ps.movies.ui.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ps.domain.modal.MovieDetailsDomainModel
import com.ps.movies.R
import com.ps.movies.ui.UiEvent
import com.ps.movies.ui.common.MovieAppBar
import com.ps.movies.ui.common.MovieBanner
import com.ps.movies.ui.details.viewModel.MovieDetailState
import com.ps.movies.ui.details.viewModel.MoviesDetailViewModel
import com.ps.movies.util.Constants
import com.ps.movies.util.TestTags
import com.ps.movies.util.safeDouble
import com.ps.movies.util.safeLong

@Composable
fun MovieDetailScreen(onBackPressed: () -> Unit) {
    val moviesDetailViewModel: MoviesDetailViewModel = hiltViewModel()
    val movieDetailsTitle = moviesDetailViewModel.movieDetailsTitleState.value

    LaunchedEffect(key1 = Unit, block = {
        moviesDetailViewModel.onEvent(UiEvent.InitState)
    })

    Scaffold(
        topBar = {
            MovieAppBar(
                title = movieDetailsTitle.toString(),
                tagName = TestTags.MOVIE_DETAIL_BACK_BUTTON,
                isBackEnabled = true,
                onBackClick = { onBackPressed.invoke() },
            )
        },
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (
                val state =
                    moviesDetailViewModel.movieDetailsState.collectAsStateWithLifecycle().value
            ) {
                is MovieDetailState.Loading -> {
                    Text(modifier = Modifier.padding(10.dp), text = Constants.MESSAGE_LOADING)
                }

                is MovieDetailState.OnMovieDetailSuccess -> {
                    DisplayMovieDetails(
                        movie = state.response,
                    )
                }

                is MovieDetailState.OnMovieDetailFailure -> {
                    Text(modifier = Modifier.padding(10.dp), text = state.message.toString())
                }
            }
        }
    }
}

@Composable
fun DisplayMovieDetails(movie: MovieDetailsDomainModel?) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .verticalScroll(state = scrollState)
            .testTag(TestTags.MOVIE_DETAIL),
    ) {
        MovieBanner(
            modifier = Modifier.height(300.dp),
            testTag = TestTags.MOVIE_DETAIL_IMAGE,
            imagePath = "${Constants.IMAGE_URL}${movie?.backdropPath.orEmpty()}",
        )

        if (movie?.genres?.isNotEmpty() == true) {
            Text(
                modifier = Modifier
                    .padding(start = 10.dp, top = 10.dp, end = 10.dp)
                    .testTag(TestTags.MOVIE_DETAIL_GENRE),
                text = stringResource(id = R.string.genre),
                color = Color.Yellow,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            )
        }
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .padding(start = 10.dp),
        ) {
            Text(
                modifier = Modifier.padding(start = 1.dp),
                text = movie?.genres?.map { it.name }.toString(),
                color = Color.White,
                fontSize = 12.sp,
            )
        }

        Row {
            Text(
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.CenterVertically)
                    .testTag(TestTags.MOVIE_DETAIL_RATING),
                text = "${stringResource(id = R.string.rating)}\n ${movie?.voteAverage?.safeDouble()}",
                color = Color.White,
            )

            val voteCount = movie?.voteCount?.safeLong()
            Text(
                modifier = Modifier
                    .padding(10.dp)
                    .testTag(TestTags.MOVIE_DETAIL_VOTE),
                text = "${stringResource(id = R.string.vote)}\n$voteCount",
                color = Color.White,
            )
        }

        Text(
            modifier = Modifier.padding(start = 10.dp, top = 10.dp, end = 10.dp),
            text = stringResource(id = R.string.production_company),
            color = Color.Yellow,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
        )
        movie?.productionCompanies?.forEach { company ->
            Text(
                modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                text = "${stringResource(id = R.string.hash_tag)} ${company.name}",
                color = Color.White,
            )
        }

        Text(
            modifier = Modifier.padding(start = 10.dp, top = 10.dp, end = 10.dp),
            text = stringResource(id = R.string.overview),
            color = Color.Yellow,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
        )
        Text(
            modifier = Modifier
                .padding(start = 10.dp, bottom = 20.dp, end = 10.dp)
                .testTag(TestTags.MOVIE_DETAIL_OVERVIEW),
            text = movie?.overview.orEmpty(),
            color = Color.White,
        )
    }
}
