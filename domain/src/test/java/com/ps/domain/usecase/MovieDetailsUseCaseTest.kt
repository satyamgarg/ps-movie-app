package com.ps.domain.usecase

import com.ps.domain.modal.MovieDetailsDomainModel
import com.ps.domain.repository.MovieDetailRepository
import com.ps.domain.utils.Result
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.net.UnknownHostException

class MovieDetailsUseCaseTest {

    private val movieDetailRepository: MovieDetailRepository = mockk()
    private lateinit var movieDetailsUseCase: MovieDetailsUseCase

    @Before
    fun setUp() {
        movieDetailsUseCase = MovieDetailsUseCase(movieDetailRepository)
    }

    @Test
    fun `fetch movie details success test`() {
        val mockMovieDetailResponse = mockk<MovieDetailsDomainModel>()
        val mockResponse = Result.Success(mockMovieDetailResponse)
        coEvery {
            movieDetailRepository.getMovieDetails(MOVIE_ID)
        } returns mockResponse

        runTest {
            assert(movieDetailsUseCase(MOVIE_ID) is Result.Success)
        }
    }

    @Test
    fun `fetch movie details error test`() {
        coEvery {
            movieDetailRepository.getMovieDetails(MOVIE_ID)
        } returns Result.Error(EMPTY_LIST)

        runTest {
            assert(movieDetailsUseCase(MOVIE_ID) is Result.Error)
        }
    }

    @Test
    fun `fetch movie details exception test`() {
        coEvery {
            movieDetailRepository.getMovieDetails(movieId = MOVIE_ID)
        } returns Result.Exception(UnknownHostException(UNKNOWN_ERROR))

        runTest {
            assert(movieDetailsUseCase(MOVIE_ID) is Result.Exception)
        }
    }

    private companion object {
        const val MOVIE_ID = "1"
        const val UNKNOWN_ERROR = "Unknown error"
        const val EMPTY_LIST = "Empty list"
    }
}
