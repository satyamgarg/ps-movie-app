package com.ps.movie.feature.details.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ps.domain.usecase.MovieDetailsUseCase
import com.ps.domain.utils.NetworkResponse
import com.ps.movie.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesDetailViewModel @Inject constructor(
    private val movieDetailsUseCase: MovieDetailsUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _movieDetailsState = MutableStateFlow<MovieDetailState>(MovieDetailState.Void)
    val movieDetailsState: StateFlow<MovieDetailState> = _movieDetailsState

    private val _movieDetailsTitleState =
        mutableStateOf<String?>(null)
    val movieDetailsTitleState: State<String?> = _movieDetailsTitleState

    init {
        savedStateHandle.get<String>(Constants.MOVIE_ID)?.let {
            getMovieDetails(it)
        }
    }

    fun getMovieDetails(movieId: String) {
        viewModelScope.launch {
            when (val response = movieDetailsUseCase(movieId = movieId)) {
                is NetworkResponse.Success -> {
                    response.data?.let {
                        _movieDetailsTitleState.value = it.title
                        _movieDetailsState.emit(MovieDetailState.OnMovieDetailSuccess(it))
                    }
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
