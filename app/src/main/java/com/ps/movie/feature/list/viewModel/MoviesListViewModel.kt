package com.ps.movie.feature.list.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ps.domain.usecase.MovieListUseCase
import com.ps.domain.utils.NetworkResponse
import com.ps.movie.feature.MovieIntent
import com.ps.movie.util.Constants
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
class MoviesListViewModel @Inject constructor(
    private val movieListUseCase: MovieListUseCase,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {

    val channel = Channel<MovieIntent>()

    private val _moviesListEvent = MutableStateFlow<MovieListEvents>(MovieListEvents.Loading)
    val movieListEvent: StateFlow<MovieListEvents> get() = _moviesListEvent

    init {
        handleChannelEvent()
    }

    private fun handleChannelEvent() {
        viewModelScope.launch {
            channel.consumeAsFlow().collect { movieIntent ->
                when (movieIntent) {
                    is MovieIntent.GetMovies -> {
                        getMoviesList()
                    }
                    else -> Unit
                }
            }
        }
    }

    fun getMoviesList() {
        _moviesListEvent.value = MovieListEvents.Loading
        viewModelScope.launch(coroutineDispatcher) {
            when (val response = movieListUseCase.getMoviesList()) {
                is NetworkResponse.Success -> {
                    _moviesListEvent.emit(MovieListEvents.OnMovieListSuccess(response.data))
                }

                is NetworkResponse.Error -> {
                    _moviesListEvent.emit(MovieListEvents.OnMovieListFailure(response.errorMessage ?: Constants.SERVER_ERROR))
                }

                is NetworkResponse.Exception -> {
                    _moviesListEvent.emit(MovieListEvents.OnMovieListFailure(response.errorMessage ?: Constants.SERVER_ERROR))
                }

                else -> {}
            }
        }
    }
}
