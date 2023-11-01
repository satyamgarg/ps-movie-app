package com.ps.movies.feature.details.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ps.domain.usecase.MovieDetailsUseCase
import com.ps.domain.utils.NetworkResponse
import com.ps.movies.feature.UiEvent
import com.ps.movies.util.Constants.MOVIE_ID
import com.ps.movies.util.Constants.SERVER_ERROR
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesDetailViewModel @Inject constructor(
    private val movieDetailsUseCase: MovieDetailsUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _movieDetailsState = MutableStateFlow<MovieDetailState>(MovieDetailState.Loading)
    val movieDetailsState: StateFlow<MovieDetailState> = _movieDetailsState

    private val _movieDetailsTitleState =
        mutableStateOf<String?>(null)
    val movieDetailsTitleState: State<String?> = _movieDetailsTitleState

    fun onEvent(uiEvent: UiEvent) {
        when (uiEvent) {
            is UiEvent.InitState -> {
                savedStateHandle.get<String>(MOVIE_ID)?.let {
                    getMovieDetails(it)
                }
            }
        }
    }

    private fun getMovieDetails(movieId: String) {
        viewModelScope.launch {
            when (val response = movieDetailsUseCase.invoke(movieId = movieId)) {
                is NetworkResponse.Success -> {
                    _movieDetailsTitleState.value = response.data.title
                    _movieDetailsState.emit(MovieDetailState.OnMovieDetailSuccess(response.data))
                }

                is NetworkResponse.Error -> {
                    _movieDetailsState.emit(MovieDetailState.OnMovieDetailFailure(response.errorMessage))
                }

                is NetworkResponse.Exception -> {
                    _movieDetailsState.emit(MovieDetailState.OnMovieDetailFailure(SERVER_ERROR))
                }
            }
        }
    }
}
