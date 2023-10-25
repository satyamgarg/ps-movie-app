package com.ps.movie.feature.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ps.domain.modal.MovieDetailResponse
import com.ps.domain.modal.MovieResult
import com.ps.movie.R
import com.ps.movie.feature.MovieIntent
import com.ps.movie.feature.common.MovieBanner
import com.ps.movie.feature.details.viewModel.MovieDetailEvent
import com.ps.movie.feature.details.viewModel.MoviesDetailViewModel
import com.ps.movie.util.Constants
import com.ps.movie.util.MoshiParser
import com.ps.movie.util.TestTags

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(movieObj: String, onBackPressed: () -> Unit) {
    val moviesDetailViewModel: MoviesDetailViewModel = hiltViewModel()
    val movie = MoshiParser().fromJson<MovieResult>(movieObj, MovieResult::class.java)
    var movieDetails by remember { mutableStateOf(MovieDetailResponse()) }
    var message by remember { mutableStateOf("") }

    LaunchedEffect(key1 = Unit, block = {
        val moviePartially = MovieDetailResponse(
            id = movie?.id,
            posterPath = movie?.posterPath,
            overview = movie?.overview,
        )
        movieDetails = moviePartially
        movieDetails.id?.let { movieId ->
            moviesDetailViewModel.channel.send(MovieIntent.GetMovieDetails(movieId = movieId))
        } ?: run { onBackPressed() }

        moviesDetailViewModel.movieDetailsEvent.collect { event ->
            when (event) {
                is MovieDetailEvent.OnMovieDetailSuccess -> {
                    event.response?.let { movieDetails = it }
                }

                is MovieDetailEvent.OnMovieDetailFailure -> {
                    event.message?.let { message = it }
                }

                else -> Unit
            }
        }
    })

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    DisplayTitle(movie?.title.orEmpty())
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black),
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.app_name),
                            tint = Color.White,
                            modifier = Modifier.testTag(TestTags.MOVIE_DETAIL_BACK_BUTTON),
                        )
                    }
                },
            )
        },
    ) { paddingValues ->
        if (movieDetails.overview?.isNotEmpty() == true) {
            DisplayMovieDetails(paddingValues = paddingValues, movie = movieDetails)
        } else {
            Text(modifier = Modifier.padding(10.dp), text = message)
        }
    }
}

@Composable
fun DisplayTitle(title: String) {
    Text(
        modifier = Modifier.testTag(TestTags.MOVIE_DETAIL_TITLE),
        text = title.ifEmpty { stringResource(id = R.string.app_name) },
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        color = Color.White,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
    )
}

@Composable
fun DisplayMovieDetails(paddingValues: PaddingValues, movie: MovieDetailResponse?) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .padding(paddingValues)
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
            if (movie?.voteAverage != null) {
                Text(
                    modifier = Modifier
                        .padding(10.dp)
                        .align(Alignment.CenterVertically)
                        .testTag(TestTags.MOVIE_DETAIL_RATING),
                    text = "${stringResource(id = R.string.rating)}\n${movie.voteAverage}",
                    color = Color.White,
                )
            }

            if (movie?.voteCount != null) {
                Text(
                    modifier = Modifier
                        .padding(10.dp)
                        .testTag(TestTags.MOVIE_DETAIL_VOTE),
                    text = "${stringResource(id = R.string.vote)}\n${movie.voteCount}",
                    color = colorResource(id = R.color.off_white),
                )
            }
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
