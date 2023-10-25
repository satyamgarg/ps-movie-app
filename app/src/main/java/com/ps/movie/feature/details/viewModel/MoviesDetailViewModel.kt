package com.ps.movie.feature.details.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ps.domain.usecase.MovieDetailsUseCase
import com.ps.domain.utils.NetworkResponse
import com.ps.movie.feature.MovieIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesDetailViewModel @Inject constructor(
    private val movieDetailsUseCase: MovieDetailsUseCase,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {

    private val _movieDetailsEvent = MutableStateFlow<MovieDetailEvent>(MovieDetailEvent.Void)
    val movieDetailsEvent: StateFlow<MovieDetailEvent> = _movieDetailsEvent

    val channel = Channel<MovieIntent>()

    init {
        handleChannelEvent()
    }

    private fun handleChannelEvent() {
        viewModelScope.launch {
            channel.consumeAsFlow().collect { movieIntent ->
                when (movieIntent) {
                    is MovieIntent.GetMovieDetails -> {
                        getMovieDetails(movieId = movieIntent.movieId)
                    }

                    else -> Unit
                }
            }
        }
    }

    fun getMovieDetails(movieId: Int) {
        _movieDetailsEvent.value = MovieDetailEvent.Loading
        viewModelScope.launch(coroutineDispatcher) {
            when (val response = movieDetailsUseCase.getMovieDetails(movieId)) {
                is NetworkResponse.Success -> {
                    _movieDetailsEvent.emit(MovieDetailEvent.OnMovieDetailSuccess(response.data))
                }

                is NetworkResponse.Error -> {
                    _movieDetailsEvent.emit(MovieDetailEvent.OnMovieDetailFailure(response.errorMessage))
                }

                is NetworkResponse.Exception -> {
                    _movieDetailsEvent.emit(MovieDetailEvent.OnMovieDetailFailure(response.errorMessage))
                }

                else -> {}
            }
        }
    }
}
