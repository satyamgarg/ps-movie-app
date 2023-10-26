package com.ps.movie.feature.list.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ps.domain.usecase.MovieListUseCase
import com.ps.domain.utils.NetworkResponse
import com.ps.movie.feature.MovieAction
import com.ps.movie.feature.MovieIntent
import com.ps.movie.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
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

    private val _moviesListState = MutableStateFlow<MovieListState>(MovieListState.Void)
    val movieListState: StateFlow<MovieListState> get() = _moviesListState

    fun initializeIntentHandler() {
        launchOnUI {
            channel.consumeAsFlow().collect { intent ->
                handleUIAction(intentToAction(intent))
            }
        }
    }

    private fun handleUIAction(intentToAction: MovieAction) {
        when (intentToAction) {
            is MovieAction.GetMovieList -> {
                getMoviesList()
            }
            else -> Unit
        }
    }

    private fun launchOnUI(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch { block() }
    }

    private fun intentToAction(intent: MovieIntent): MovieAction {
        return when (intent) {
            is MovieIntent.GetMovies -> MovieAction.GetMovieList
            else -> MovieAction.None
        }
    }
    fun getMoviesList() {
        _moviesListState.value = MovieListState.Loading
        viewModelScope.launch(coroutineDispatcher) {
            when (val response = movieListUseCase.getMoviesList()) {
                is NetworkResponse.Success -> {
                    _moviesListState.emit(MovieListState.OnMovieListSuccess(response.data))
                }

                is NetworkResponse.Error -> {
                    _moviesListState.emit(
                        MovieListState.OnMovieListFailure(
                            response.errorMessage ?: Constants.SERVER_ERROR,
                        ),
                    )
                }

                is NetworkResponse.Exception -> {
                    _moviesListState.emit(
                        MovieListState.OnMovieListFailure(
                            response.errorMessage ?: Constants.SERVER_ERROR,
                        ),
                    )
                }

                else -> {}
            }
        }
    }
}
