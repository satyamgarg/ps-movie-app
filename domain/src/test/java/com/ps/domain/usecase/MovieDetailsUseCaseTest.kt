package com.ps.domain.usecase

import com.ps.domain.modal.MovieDetailResponse
import com.ps.domain.repository.MovieRepository
import com.ps.domain.utils.Constants
import com.ps.domain.utils.NetworkResponse
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

    private lateinit var movieDetailsUseCase: MovieDetailsUseCase

    @Before
    fun setUp() {
        Dispatchers.setMain(coroutineTestDispatcher)
        MockKAnnotations.init(this)

        movieDetailsUseCase = MovieDetailsUseCase(movieRepository)
    }

    @Test
    fun `fetch movie details success test`() {
        val mockMovieDetailResponse = mockk<MovieDetailResponse>()

        val mockResponse = NetworkResponse.Success(mockMovieDetailResponse)
        coEvery {
            movieDetailsUseCase.invoke(movieId = 1)
        } returns mockResponse

        runTest {
            assert(movieDetailsUseCase.invoke(movieId = 1) is NetworkResponse.Success)
        }
    }

    @Test
    fun `fetch movie details error test`() {
        coEvery {
            movieDetailsUseCase.invoke(movieId = 1)
        } returns NetworkResponse.Error(Constants.EMPTY_LIST)

        runTest {
            assert(movieDetailsUseCase.invoke(1) is NetworkResponse.Error)
        }
    }

    @Test
    fun `fetch movie details exception test`() {
        coEvery {
            movieDetailsUseCase.invoke(1)
        } returns NetworkResponse.Exception(UnknownHostException(Constants.UNKNOWN_ERROR))

        runTest {
            assert(movieDetailsUseCase.invoke(1) is NetworkResponse.Exception)
        }
    }
}
