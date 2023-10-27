package com.ps.domain.usecase

import com.ps.domain.modal.MovieListResponse
import com.ps.domain.modal.MovieResult
import com.ps.domain.repository.MovieRepository
import com.ps.domain.utils.Constants
import com.ps.domain.utils.NetworkResponse
import io.mockk.coEvery
import io.mockk.every
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.net.UnknownHostException

@OptIn(ExperimentalCoroutinesApi::class)
class MovieListUseCaseTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    private val movieRepository: MovieRepository = mockk()

    private lateinit var movieListUseCase: MovieListUseCase

    @Before
    fun setUp() {
        movieListUseCase = MovieListUseCase(
            movieRepository = movieRepository,
        )
    }

    @Test
    fun `fetch movie list success test`() {
        val mockMovieResult = mockk<MovieResult>()
        val mockMovieListResponse = mockk<MovieListResponse>()

        every {
            mockMovieListResponse.results
        } returns listOf(mockMovieResult)

        coEvery {
            movieRepository.getMoviesList()
        } returns NetworkResponse.Success(mockMovieListResponse)

        runTest {
            val data = movieListUseCase()
            assert(data is NetworkResponse.Success)
        }
    }

    @Test
    fun `fetch movie list failure test`() {
        coEvery {
            movieRepository.getMoviesList()
        } returns NetworkResponse.Error(Constants.API_ERROR)

        runTest {
            assert(movieListUseCase() is NetworkResponse.Error)
        }
    }

    @Test
    fun `fetch movie list exception test`() {
        coEvery {
            movieRepository.getMoviesList()
        } returns NetworkResponse.Exception(UnknownHostException(Constants.UNKNOWN_ERROR))

        runTest {
            assert(movieListUseCase() is NetworkResponse.Exception)
        }
    }
}
