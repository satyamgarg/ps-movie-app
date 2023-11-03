package com.ps.movies.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.ps.domain.modal.MovieDetailsDomainModel
import com.ps.domain.usecase.MovieDetailsUseCase
import com.ps.domain.utils.Result
import com.ps.movies.core.CoroutineRule
import com.ps.movies.ui.UiEvent
import com.ps.movies.ui.details.viewModel.MovieDetailState
import com.ps.movies.ui.details.viewModel.MoviesDetailViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@OptIn(ExperimentalCoroutinesApi::class)
class MovieDetailsViewModelTestDomainModel {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = CoroutineRule()

    private lateinit var viewModel: MoviesDetailViewModel

    private val movieDetailsUseCase: MovieDetailsUseCase = mockk()

    companion object {
        private const val MOVIE_ID = "1"
        private const val MOVIE_KEY = "movieId"
        private const val EMPTY_LIST = "Empty list"
        private const val EMPTY_STRING = ""
    }

    private val savedState = SavedStateHandle(mapOf(MOVIE_KEY to MOVIE_ID))

    @Before
    fun setUp() {
        viewModel = MoviesDetailViewModel(movieDetailsUseCase, savedStateHandle = savedState)
    }

    @Test
    fun `fetch movie detail loading success test`() {
        coEvery {
            movieDetailsUseCase(MOVIE_ID)
        } returns Result.Success(MovieDetailsDomainModel())

        runTest {
            viewModel.onEvent(UiEvent.InitState)
            assert(viewModel.movieDetailsState.value is MovieDetailState.OnMovieDetailSuccess)
        }
    }

    @Test
    fun `fetch movie detail error failure test`() {
        coEvery {
            movieDetailsUseCase(MOVIE_ID)
        } returns Result.Error(errorMessage = EMPTY_STRING)

        runTest {
            viewModel.onEvent(UiEvent.InitState)
            assert(viewModel.movieDetailsState.value is MovieDetailState.OnMovieDetailFailure)
        }
    }

    @Test
    fun `fetch movie detail exception failure test`() {
        coEvery {
            movieDetailsUseCase(MOVIE_ID)
        } returns Result.Exception(Exception(EMPTY_LIST))

        runTest {
            viewModel.onEvent(UiEvent.InitState)
            assert(viewModel.movieDetailsState.value is MovieDetailState.OnMovieDetailFailure)
        }
    }
}
