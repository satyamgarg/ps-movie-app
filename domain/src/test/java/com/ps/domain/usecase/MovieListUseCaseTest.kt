package com.ps.domain.usecase

import com.ps.domain.modal.MovieListResponse
import com.ps.domain.modal.MovieResult
import com.ps.domain.repository.MovieRepository
import com.ps.domain.utils.Constants
import com.ps.domain.utils.NetworkResponse
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
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

    private lateinit var movieListUseCase: MovieListUseCase

    @Before
    fun setUp() {
        Dispatchers.setMain(coroutineTestDispatcher)
        MockKAnnotations.init(this)
        movieListUseCase = MovieListUseCase(
            movieRepository = movieRepository,
        )
    }

    @Test
    fun `fetch movie list success test`() {
        val mockMovieResult = mockk<MovieResult>()
        val mockMovieListResponse = mockk<MovieListResponse>()

        every {
            mockMovieResult.id
        } returns 10

        every {
            mockMovieListResponse.results
        } returns listOf(mockMovieResult)

        coEvery {
            movieListUseCase.getMoviesList()
        } returns NetworkResponse.Success(mockMovieListResponse)

        runTest {
            val data = movieListUseCase.getMoviesList()
            assert(data is NetworkResponse.Success)
        }
    }

    @Test
    fun `fetch movie list failure test`() {
        coEvery {
            movieListUseCase.getMoviesList()
        } returns NetworkResponse.Error(Constants.API_ERROR)

        runTest {
            assert(movieListUseCase.getMoviesList() is NetworkResponse.Error)
        }
    }

    @Test
    fun `fetch movie list exception test`() {
        coEvery {
            movieListUseCase.getMoviesList()
        } returns NetworkResponse.Exception(UnknownHostException(Constants.UNKNOWN_ERROR))

        runTest {
            assert(movieListUseCase.getMoviesList() is NetworkResponse.Exception)
        }
    }
}
