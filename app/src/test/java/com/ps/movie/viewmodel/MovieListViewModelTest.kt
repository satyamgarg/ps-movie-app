package com.ps.movie.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ps.domain.modal.MovieListResponse
import com.ps.domain.usecase.MovieListUseCase
import com.ps.domain.utils.Constants
import com.ps.domain.utils.NetworkResponse
import com.ps.movie.core.CoroutineRule
import com.ps.movie.feature.UiEvent
import com.ps.movie.feature.list.viewModel.MovieListState
import com.ps.movie.feature.list.viewModel.MoviesListViewModel
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
class MovieListViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = CoroutineRule()

    @get:Rule
    val mockkRule = MockKRule(this)

    private val movieListUseCase: MovieListUseCase = mockk()

    private lateinit var viewModel: MoviesListViewModel

    @Before
    fun setUp() {
        viewModel = MoviesListViewModel(movieListUseCase)
    }

    @Test
    fun `fetch movie detail loading success test`() {
        coEvery {
            movieListUseCase()
        } returns NetworkResponse.Success(MovieListResponse(results = emptyList()))

        runTest {
            viewModel.onEvent(UiEvent.InitState)
            assert(viewModel.movieListState.value is MovieListState.OnMovieListSuccess)
        }
    }

    @Test
    fun `fetch movie detail error failure test`() {
        coEvery {
            movieListUseCase()
        } returns NetworkResponse.Error(errorMessage = Constants.EMPTY_LIST)

        runTest {
            viewModel.onEvent(UiEvent.InitState)
            assert(viewModel.movieListState.value is MovieListState.OnMovieListFailure)
        }
    }

    @Test
    fun `fetch movie detail exception failure test`() {
        coEvery {
            movieListUseCase()
        } returns NetworkResponse.Exception(Exception(Constants.EMPTY_LIST))

        runTest {
            viewModel.onEvent(UiEvent.InitState)
            assert(viewModel.movieListState.value is MovieListState.OnMovieListFailure)
        }
    }
}
