package com.ps.movie.feature.details

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ps.domain.modal.MovieDetailResponse
import com.ps.movie.R
import com.ps.movie.feature.common.MovieAppBar
import com.ps.movie.feature.common.MovieBanner
import com.ps.movie.feature.details.viewModel.MovieDetailState
import com.ps.movie.feature.details.viewModel.MoviesDetailViewModel
import com.ps.movie.util.Constants
import com.ps.movie.util.TestTags
import com.ps.movie.util.safeDouble
import com.ps.movie.util.safeLong

@Composable
fun MovieDetailScreen(onBackPressed: () -> Unit) {
    val moviesDetailViewModel: MoviesDetailViewModel = hiltViewModel()

    val movieDetailsTitle = moviesDetailViewModel.movieDetailsTitleState.value

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
                is MovieDetailState.OnMovieDetailSuccess -> {
                    DisplayMovieDetails(
                        movie = state.response,
                    )
                }

                is MovieDetailState.OnMovieDetailFailure -> {
                    Text(modifier = Modifier.padding(10.dp), text = state.message.toString())
                }

                else -> Unit
            }
        }
    }
}

@Composable
fun DisplayMovieDetails(movie: MovieDetailResponse?) {
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
                text = movie?.genres?.map { it?.name }.toString(),
                color = colorResource(id = R.color.off_white),
                fontSize = 12.sp,
            )
        }

        Row {
            val rating = movie?.voteAverage?.safeDouble()
            Text(
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.CenterVertically)
                    .testTag(TestTags.MOVIE_DETAIL_RATING),
                text = "${stringResource(id = R.string.rating)}\n $rating",
                color = Color.White,
            )

            val voteCount = movie?.voteCount?.safeLong()
            Text(
                modifier = Modifier
                    .padding(10.dp)
                    .testTag(TestTags.MOVIE_DETAIL_VOTE),
                text = "${stringResource(id = R.string.vote)}\n$voteCount",
                color = colorResource(id = R.color.off_white),
            )
        }

        if (movie?.productionCompanies?.isNotEmpty() == true) {
            Text(
                modifier = Modifier.padding(start = 10.dp, top = 10.dp, end = 10.dp),
                text = stringResource(id = R.string.production_company),
                color = Color.Yellow,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            )
        }
        movie?.productionCompanies?.forEach { company ->
            Text(
                modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                text = "${stringResource(id = R.string.hash_tag)} ${company?.name}",
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
            color = colorResource(id = R.color.off_white),
        )
    }
}
