package com.ps.movie.viewmodel

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

@OptIn(ExperimentalCoroutinesApi::class)
class MovieDetailsViewModelTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    private val coroutineTestDispatcher = StandardTestDispatcher()

    @MockK
    private lateinit var movieRepository: MovieRepository

    private lateinit var viewModel: MoviesDetailViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(coroutineTestDispatcher)
        MockKAnnotations.init(this)
        viewModel = MoviesDetailViewModel(
            movieDetailsUseCase = MovieDetailsUseCase(movieRepository),
            coroutineDispatcher = coroutineTestDispatcher,
        )
    }

    @Test
    fun `fetch movie detail success test`() {
        val mockMovieDetailResponse = mockk<MovieDetailResponse>()

        coEvery {
            movieRepository.getMovieDetails(1)
        } returns NetworkResponse.Success(mockMovieDetailResponse)

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
    fun `fetch movie detail error failure test`() {
        coEvery {
            movieRepository.getMovieDetails(1)
        } returns NetworkResponse.Error(errorMessage = Constants.EMPTY_LIST)

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
    fun `fetch movie detail exception failure test`() {
        coEvery {
            movieRepository.getMovieDetails(1)
        } returns NetworkResponse.Exception(Exception(Constants.EMPTY_LIST))

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
}
