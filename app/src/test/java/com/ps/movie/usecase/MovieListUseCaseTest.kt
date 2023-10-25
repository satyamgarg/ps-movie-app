package com.ps.movie.usecase

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
import java.net.UnknownHostException

@OptIn(ExperimentalCoroutinesApi::class)
class MovieListUseCaseTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    private lateinit var movieRepository: MovieRepository

    private val coroutineTestDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: MoviesListViewModel

    private lateinit var movieListUseCase: MovieListUseCase

    @Before
    fun setUp() {
        Dispatchers.setMain(coroutineTestDispatcher)
        MockKAnnotations.init(this)
        movieListUseCase = MovieListUseCase(
            movieRepository = movieRepository,
        )
        viewModel = MoviesListViewModel(
            movieListUseCase = MovieListUseCase(movieRepository),
            coroutineDispatcher = coroutineTestDispatcher,
        )
    }

    @Test
    fun `fetch movie list success test`() {
        val mockMovieListResponse = mockk<MovieListResponse>()

        val mockResponse = NetworkResponse.Success(mockMovieListResponse)
        coEvery {
            movieListUseCase.getMoviesList()
        } returns mockResponse

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
    fun `fetch movie list failure test`() {
        coEvery {
            movieListUseCase.getMoviesList()
        } returns NetworkResponse.Error(Constants.API_ERROR)

        viewModel.getMoviesList()

        runTest {
            viewModel.movieListEvent.test {
                val result = awaitItem()
                if (result is MovieListEvents.OnMovieListFailure) {
                    assert(result.message == Constants.API_ERROR)
                    awaitComplete()
                }
            }
        }
    }

    @Test
    fun `fetch movie list exception test`() {
        coEvery {
            movieListUseCase.getMoviesList()
        } returns NetworkResponse.Exception(UnknownHostException(Constants.UNKNOWN_ERROR))

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
