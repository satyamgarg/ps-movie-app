package com.ps.movies.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.ps.domain.modal.MovieDetailResponse
import com.ps.domain.usecase.MovieDetailsUseCase
import com.ps.domain.utils.Constants
import com.ps.domain.utils.NetworkResponse
import com.ps.movies.core.CoroutineRule
import com.ps.movies.feature.UiEvent
import com.ps.movies.feature.details.viewModel.MovieDetailState
import com.ps.movies.feature.details.viewModel.MoviesDetailViewModel
import io.mockk.coEvery
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@OptIn(ExperimentalCoroutinesApi::class)
class MovieDetailsViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = CoroutineRule()

    private lateinit var viewModel: MoviesDetailViewModel

    @get:Rule
    val mockkRule = MockKRule(this)

    private val movieDetailsUseCase: MovieDetailsUseCase = mockk()

    private val savedState = SavedStateHandle(mapOf("movieId" to "1"))

    @Before
    fun setUp() {
        viewModel = MoviesDetailViewModel(movieDetailsUseCase, savedStateHandle = savedState)
    }

    @Test
    fun `fetch movie detail loading success test`() {
        coEvery {
            movieDetailsUseCase("1")
        } returns NetworkResponse.Success(MovieDetailResponse())

        runTest {
            viewModel.onEvent(UiEvent.InitState)
            assert(viewModel.movieDetailsState.value is MovieDetailState.OnMovieDetailSuccess)
        }
    }

    @Test
    fun `fetch movie detail error failure test`() {
        coEvery {
            movieDetailsUseCase("1")
        } returns NetworkResponse.Error(errorMessage = Constants.EMPTY_LIST)

        runTest {
            viewModel.onEvent(UiEvent.InitState)
            assert(viewModel.movieDetailsState.value is MovieDetailState.OnMovieDetailFailure)
        }
    }

    @Test
    fun `fetch movie detail exception failure test`() {
        coEvery {
            movieDetailsUseCase("1")
        } returns NetworkResponse.Exception(Exception(Constants.EMPTY_LIST))

        runTest {
            viewModel.onEvent(UiEvent.InitState)
            assert(viewModel.movieDetailsState.value is MovieDetailState.OnMovieDetailFailure)
        }
    }
}
