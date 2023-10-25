package com.ps.movie.usecase

import app.cash.turbine.test
import com.ps.domain.modal.MovieDetailResponse
import com.ps.domain.repository.MovieRepository
import com.ps.domain.usecase.MovieDetailsUseCase
import com.ps.domain.utils.Constants
import com.ps.domain.utils.NetworkResponse
import com.ps.movie.feature.details.viewModel.MovieDetailEvent
import com.ps.movie.feature.details.viewModel.MoviesDetailViewModel
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
class MovieDetailsUseCaseTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    private lateinit var movieRepository: MovieRepository

    private val coroutineTestDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: MoviesDetailViewModel

    private lateinit var movieDetailsUseCase: MovieDetailsUseCase

    @Before
    fun setUp() {
        Dispatchers.setMain(coroutineTestDispatcher)
        MockKAnnotations.init(this)

        viewModel = MoviesDetailViewModel(
            movieDetailsUseCase = MovieDetailsUseCase(movieRepository),
            coroutineDispatcher = coroutineTestDispatcher,
        )

        movieDetailsUseCase = MovieDetailsUseCase(movieRepository)
    }

    @Test
    fun `fetch movie details success test`() {
        val mockMovieDetailResponse = mockk<MovieDetailResponse>()

        val mockResponse = NetworkResponse.Success(mockMovieDetailResponse)
        coEvery {
            movieDetailsUseCase.getMovieDetails(1)
        } returns mockResponse

        viewModel.getMovieDetails(1)

        runTest {
            viewModel.movieDetailsEvent.test {
                val result = awaitItem()
                if (result is MovieDetailEvent.OnMovieDetailSuccess) {
                    assert(result.response != null)
                    awaitComplete()
                }
            }
        }
    }

    @Test
    fun `fetch movie details error test`() {
        coEvery {
            movieDetailsUseCase.getMovieDetails(1)
        } returns NetworkResponse.Error(Constants.EMPTY_LIST)

        viewModel.getMovieDetails(1)

        runTest {
            viewModel.movieDetailsEvent.test {
                val result = awaitItem()
                if (result is MovieDetailEvent.OnMovieDetailFailure) {
                    assert(result.message == Constants.EMPTY_LIST)
                    awaitComplete()
                }
            }
        }
    }

    @Test
    fun `fetch movie details exception test`() {
        coEvery {
            movieDetailsUseCase.getMovieDetails(1)
        } returns NetworkResponse.Exception(UnknownHostException(Constants.UNKNOWN_ERROR))

        viewModel.getMovieDetails(1)

        runTest {
            viewModel.movieDetailsEvent.test {
                val result = awaitItem()
                if (result is MovieDetailEvent.OnMovieDetailFailure) {
                    assert(result.message == Constants.UNKNOWN_ERROR)
                    awaitComplete()
                }
            }
        }
    }
}
