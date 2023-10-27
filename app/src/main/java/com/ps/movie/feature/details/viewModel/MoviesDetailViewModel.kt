package com.ps.movie.feature.details.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ps.domain.usecase.MovieDetailsUseCase
import com.ps.domain.utils.NetworkResponse
import com.ps.movie.feature.MovieAction
import com.ps.movie.feature.MovieIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesDetailViewModel @Inject constructor(
    private val movieDetailsUseCase: MovieDetailsUseCase,
) : ViewModel() {

    val channel = Channel<MovieIntent>()

    private val _movieDetailsState = MutableStateFlow<MovieDetailState>(MovieDetailState.Void)
    val movieDetailsState: StateFlow<MovieDetailState> = _movieDetailsState

    fun initializeIntentHandler() {
        launchOnUI {
            channel.consumeAsFlow().collect { intent ->
                handleUIAction(intentToAction(intent))
            }
        }
    }

    private fun handleUIAction(intentToAction: MovieAction) {
        when (intentToAction) {
            is MovieAction.GetMovieDetails -> {
                getMovieDetails(movieId = intentToAction.movieId)
            }

            is MovieAction.DisplayAvailableDetails -> {
                launchOnUI {
                    _movieDetailsState.emit(MovieDetailState.OnMovieDetailSuccess(response = intentToAction.movieDetailResponse))
                }
            }

            else -> Unit
        }
    }

    private fun launchOnUI(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch { block() }
    }

    private fun intentToAction(intent: MovieIntent): MovieAction {
        return when (intent) {
            is MovieIntent.GetMovieDetails -> MovieAction.GetMovieDetails(intent.movieId)
            is MovieIntent.DisplayAvailableDetails -> MovieAction.DisplayAvailableDetails(intent.movieDetailResponse)
            else -> MovieAction.None
        }
    }

    fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            when (val response = movieDetailsUseCase.invoke(movieId = movieId)) {
                is NetworkResponse.Success -> {
                    _movieDetailsState.emit(MovieDetailState.OnMovieDetailSuccess(response.data))
                }

                is NetworkResponse.Error -> {
                    _movieDetailsState.emit(MovieDetailState.OnMovieDetailFailure(response.errorMessage))
                }

                is NetworkResponse.Exception -> {
                    _movieDetailsState.emit(MovieDetailState.OnMovieDetailFailure(response.errorMessage))
                }

                else -> {}
            }
        }
    }
}
