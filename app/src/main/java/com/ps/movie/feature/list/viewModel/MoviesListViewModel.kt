package com.ps.movie.feature.list.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ps.domain.usecase.MovieListUseCase
import com.ps.domain.utils.NetworkResponse
import com.ps.movie.feature.UiEvent
import com.ps.movie.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel @Inject constructor(
    val movieListUseCase: MovieListUseCase,
) : ViewModel() {

    private val _moviesListState = MutableStateFlow<MovieListState>(MovieListState.Loading)
    val movieListState: StateFlow<MovieListState> get() = _moviesListState

    fun onEvent(uiEvent: UiEvent) {
        viewModelScope.launch {
            when (uiEvent) {
                is UiEvent.InitState -> {
                    getMoviesList()
                }
            }
        }
    }

    private fun getMoviesList() {
        viewModelScope.launch {
            when (val response = movieListUseCase()) {
                is NetworkResponse.Success -> {
                    _moviesListState.emit(MovieListState.OnMovieListSuccess(response.data))
                }

                is NetworkResponse.Error -> {
                    _moviesListState.emit(MovieListState.OnMovieListFailure(Constants.SERVER_ERROR))
                }

                is NetworkResponse.Exception -> {
                    _moviesListState.emit(MovieListState.OnMovieListFailure(Constants.SERVER_ERROR))
                }

                else -> {}
            }
        }
    }
}
