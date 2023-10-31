package com.ps.movie.feature.list.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ps.domain.usecase.MovieListUseCase
import com.ps.domain.utils.NetworkResponse
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

    private val _moviesListState = MutableStateFlow<MovieListState>(MovieListState.Void)
    val movieListState: StateFlow<MovieListState> get() = _moviesListState

    init {
        getMoviesList()
    }

    fun getMoviesList() {
        _moviesListState.value = MovieListState.Loading
        viewModelScope.launch {
            when (val response = movieListUseCase()) {
                is NetworkResponse.Success -> {
                    response.data?.let {
                        _moviesListState.emit(MovieListState.OnMovieListSuccess(it))
                    } ?: run {
                        _moviesListState.emit(MovieListState.OnMovieListFailure(Constants.SERVER_ERROR))
                    }
                }

                is NetworkResponse.Error -> {
                    _moviesListState.emit(
                        MovieListState.OnMovieListFailure(
                            Constants.SERVER_ERROR,
                        ),
                    )
                }

                is NetworkResponse.Exception -> {
                    _moviesListState.emit(
                        MovieListState.OnMovieListFailure(
                            Constants.SERVER_ERROR,
                        ),
                    )
                }

                else -> {}
            }
        }
    }
}
