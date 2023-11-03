package com.ps.movies.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ps.domain.modal.MovieListDomainModel
import com.ps.domain.usecase.MovieListUseCase
import com.ps.domain.utils.Result
import com.ps.movies.core.CoroutineRule
import com.ps.movies.ui.UiEvent
import com.ps.movies.ui.list.viewModel.MovieListState
import com.ps.movies.ui.list.viewModel.MoviesListViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.net.UnknownHostException

@OptIn(ExperimentalCoroutinesApi::class)
class MovieListDomainModelViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = CoroutineRule()

    private val movieListUseCase: MovieListUseCase = mockk()

    private lateinit var viewModel: MoviesListViewModel

    companion object {
        private const val EMPTY_LIST = "Empty list"
        private const val EXCEPTION_MSG = "unknown-host exception"
    }

    @Before
    fun setUp() {
        viewModel = MoviesListViewModel(movieListUseCase)
    }

    @Test
    fun `fetch movie detail loading success test`() {
        coEvery {
            movieListUseCase()
        } returns Result.Success(MovieListDomainModel(results = emptyList()))

        runTest {
            viewModel.onEvent(UiEvent.InitState)
            assert(viewModel.movieListState.value is MovieListState.OnMovieListSuccess)
        }
    }

    @Test
    fun `fetch movie detail error failure test`() {
        coEvery {
            movieListUseCase()
        } returns Result.Error(errorMessage = EMPTY_LIST)

        runTest {
            viewModel.onEvent(UiEvent.InitState)
            assert(viewModel.movieListState.value is MovieListState.OnMovieListFailure)
        }
    }

    @Test
    fun `fetch movie detail exception failure test`() {
        coEvery {
            movieListUseCase()
        } returns Result.Exception(UnknownHostException(EXCEPTION_MSG))

        runTest {
            viewModel.onEvent(UiEvent.InitState)
            assert(viewModel.movieListState.value is MovieListState.OnMovieListFailure)
        }
    }
}
