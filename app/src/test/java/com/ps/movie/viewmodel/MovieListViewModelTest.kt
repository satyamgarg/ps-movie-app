package com.ps.movie.viewmodel

import app.cash.turbine.test
import com.ps.domain.modal.MovieListResponse
import com.ps.domain.repository.MovieRepository
import com.ps.domain.usecase.MovieListUseCase
import com.ps.domain.utils.Constants
import com.ps.domain.utils.NetworkResponse
import com.ps.movie.feature.list.viewModel.MovieListEvents
import com.ps.movie.feature.list.viewModel.MoviesListViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MovieListViewModelTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    private val coroutineTestDispatcher = StandardTestDispatcher()

    @MockK
    private lateinit var movieRepository: MovieRepository

    private lateinit var viewModel: MoviesListViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(coroutineTestDispatcher)
        MockKAnnotations.init(this)
        viewModel = MoviesListViewModel(
            movieListUseCase = MovieListUseCase(movieRepository),
            coroutineDispatcher = coroutineTestDispatcher,
        )
    }

    @Test
    fun `fetch movie list success test`() {
        val mockMovieListResponse = mockk<MovieListResponse>()

        coEvery {
            movieRepository.getMoviesList()
        } returns NetworkResponse.Success(mockMovieListResponse)

        viewModel.getMoviesList()

        runTest {
            viewModel.movieListEvent.test {
                val result = awaitItem()
                if (result is MovieListEvents.OnMovieListSuccess) {
                    assert(result.response?.results?.size == 1)
                    awaitComplete()
                }
            }
        }
    }

    @Test
    fun `fetch movie detail empty list failure test`() {
        coEvery {
            movieRepository.getMoviesList()
        } returns NetworkResponse.Error(errorMessage = Constants.EMPTY_LIST)

        viewModel.getMoviesList()

        runTest {
            viewModel.movieListEvent.test {
                val result = awaitItem()
                if (result is MovieListEvents.OnMovieListFailure) {
                    assert(result.message == Constants.EMPTY_LIST)
                    awaitComplete()
                }
            }
        }
    }

    @Test
    fun `fetch movie list empty exception failure test`() {
        coEvery {
            movieRepository.getMoviesList()
        } returns NetworkResponse.Exception(Exception(Constants.UNKNOWN_ERROR))

        viewModel.getMoviesList()

        runTest {
            viewModel.movieListEvent.test {
                val result = awaitItem()
                if (result is MovieListEvents.OnMovieListFailure) {
                    assert(result.message == Constants.UNKNOWN_ERROR)
                    awaitComplete()
                }
            }
        }
    }
}
