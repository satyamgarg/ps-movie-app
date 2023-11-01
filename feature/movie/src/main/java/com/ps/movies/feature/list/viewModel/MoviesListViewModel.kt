package com.ps.movies.feature.list.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ps.domain.usecase.MovieListUseCase
import com.ps.domain.utils.NetworkResponse
import com.ps.movies.feature.UiEvent
import com.ps.movies.util.Constants.SERVER_ERROR
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel @Inject constructor(
    private val movieListUseCase: MovieListUseCase,
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
            when (val response = movieListUseCase.invoke()) {
                is NetworkResponse.Success -> {
                    _moviesListState.emit(MovieListState.OnMovieListSuccess(response.data))
                }

                is NetworkResponse.Error -> {
                    _moviesListState.emit(MovieListState.OnMovieListFailure(SERVER_ERROR))
                }

                is NetworkResponse.Exception -> {
                    _moviesListState.emit(MovieListState.OnMovieListFailure(SERVER_ERROR))
                }

                else -> {}
            }
        }
    }
}
