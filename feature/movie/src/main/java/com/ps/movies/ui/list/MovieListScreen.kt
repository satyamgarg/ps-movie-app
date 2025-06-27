package com.ps.movies.ui.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.ps.domain.modal.MovieResultDomainModel
import com.ps.movies.R
import com.ps.movies.theme.LocalDimension
import com.ps.movies.ui.UiEvent
import com.ps.movies.ui.list.viewModel.MovieListState
import com.ps.movies.ui.list.viewModel.MoviesListViewModel
import com.ps.movies.util.Constants
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieAppBar(
    title: String,
    isBackEnabled: Boolean,
    onBackClick: () -> Unit = {},
) {
    TopAppBar(
        title = {
            DisplayTitle(title)
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black),
        navigationIcon = {
            if (isBackEnabled) {
                IconButton(onClick = { onBackClick.invoke() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.app_name),
                        tint = MaterialTheme.colorScheme.primary,
                    )
                }
            }
        },
    )
}

@Composable
fun MovieBanner(
    modifier: Modifier,
    imagePath: String,
    onMovieClick: (() -> Unit)? = null,
) {
    val localDims = LocalDimension.current
    GlideImage(
        modifier = modifier.height(localDims.size400)
            .padding(localDims.padding10).fillMaxWidth(1f)
            .clip(RoundedCornerShape(localDims.size5)).clickable {
                onMovieClick?.invoke()
            },
        imageOptions = ImageOptions(contentScale = ContentScale.FillBounds),
        imageModel = {
            imagePath
        },
        loading = {
            Box(
                modifier = Modifier.fillMaxWidth().background(color = Color.Gray)
                    .height(localDims.size300),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        },
        requestOptions = { RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE) },
        failure = {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = stringResource(id = R.string.failed_to_load_image),
                tint = MaterialTheme.colorScheme.error,
            )
        },
    )
}

@Composable
fun DisplayTitle(title: String) {
    Text(
        text = title.ifEmpty { stringResource(id = R.string.app_name) },
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style = MaterialTheme.typography.headlineMedium,
    )
}
