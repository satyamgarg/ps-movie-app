package com.ps.movies.ui.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ps.domain.modal.MovieDetailsDomainModel
import com.ps.movies.R
import com.ps.movies.theme.LocalDimension
import com.ps.movies.ui.UiEvent
import com.ps.movies.ui.common.MovieAppBar
import com.ps.movies.ui.common.MovieBanner
import com.ps.movies.ui.details.viewModel.MovieDetailState
import com.ps.movies.ui.details.viewModel.MoviesDetailViewModel
import com.ps.movies.util.Constants
import com.ps.movies.util.safeDouble
import com.ps.movies.util.safeLong

@Composable
fun MovieDetailScreen(onBackPressed: () -> Unit) {
    val localDims = LocalDimension.current
    val moviesDetailViewModel: MoviesDetailViewModel = hiltViewModel()
    val movieDetailsTitle = moviesDetailViewModel.movieDetailsTitleState.value

    LaunchedEffect(key1 = Unit, block = {
        moviesDetailViewModel.onEvent(UiEvent.InitState)
    })

    Scaffold(
        topBar = {
            MovieAppBar(
                title = movieDetailsTitle,
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
                    Text(
                        modifier = Modifier.padding(localDims.padding10),
                        text = Constants.MESSAGE_LOADING,
                    )
                }

                is MovieDetailState.OnMovieDetailSuccess -> {
                    DisplayMovieDetails(
                        movie = state.response,
                    )
                }

                is MovieDetailState.OnMovieDetailFailure -> {
                    Text(modifier = Modifier.padding(localDims.padding10), text = state.message)
                }
            }
        }
    }
}

@Composable
fun DisplayMovieDetails(movie: MovieDetailsDomainModel) {
    val scrollState = rememberScrollState()
    val localDims = LocalDimension.current
    Column(
        modifier = Modifier
            .verticalScroll(state = scrollState),
    ) {
        MovieBanner(
            modifier = Modifier,
            imagePath = "${Constants.IMAGE_URL}${movie.backdropPath}",
        )

        Text(
            modifier = Modifier
                .padding(localDims.padding10),
            text = stringResource(id = R.string.genre),
            style = MaterialTheme.typography.titleMedium,
        )
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .padding(start = localDims.padding10),
        ) {
            Text(
                modifier = Modifier.padding(start = localDims.padding1),
                text = movie.genres.map { it.name }.toString(),
                style = MaterialTheme.typography.labelMedium,
            )
        }

        Row {
            Text(
                modifier = Modifier
                    .padding(localDims.padding10)
                    .align(Alignment.CenterVertically),
                text = "${stringResource(id = R.string.rating)}\n ${movie.voteAverage.safeDouble()}",
                style = MaterialTheme.typography.labelMedium,
            )

            val voteCount = movie.voteCount.safeLong()
            Text(
                modifier = Modifier
                    .padding(localDims.padding10),
                text = "${stringResource(id = R.string.vote)}\n$voteCount",
                style = MaterialTheme.typography.labelMedium,
            )
        }

        Text(
            modifier = Modifier.padding(
                start = localDims.padding10,
                top = localDims.padding10,
                end = localDims.padding10,
            ),
            text = stringResource(id = R.string.production_company),
            style = MaterialTheme.typography.titleMedium,
        )
        movie.productionCompanies.forEach { company ->
            Text(
                modifier = Modifier.padding(horizontal = localDims.padding10),
                text = "${stringResource(id = R.string.hash_tag)} ${company.name}",
                style = MaterialTheme.typography.labelMedium,
            )
        }

        Text(
            modifier = Modifier.padding(
                start = localDims.padding10,
                top = localDims.padding10,
                end = localDims.padding10,
            ),
            text = stringResource(id = R.string.overview),
            style = MaterialTheme.typography.titleMedium,
        )
        Text(
            modifier = Modifier
                .padding(start = localDims.padding10, bottom = localDims.padding20, end = localDims.padding10),
            text = movie.overview,
            style = MaterialTheme.typography.labelMedium,
        )
    }
}
