package com.ps.data.dto.repository

import com.ps.data.dto.MovieDetailResponseDto
import com.ps.data.dto.MovieListResponseDto
import com.ps.data.remote.MovieService
import com.ps.data.repository.MovieRepositoryImpl
import com.ps.domain.modal.MovieDetailResponse
import com.ps.domain.modal.MovieListResponse
import com.ps.domain.utils.NetworkResponse
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response
import java.net.UnknownHostException

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class MovieRepositoryImplTest {

    private lateinit var movieRepository: MovieRepositoryImpl
    private val movieService = mockk<MovieService>()

    @Before
    fun setUp() {
        movieRepository = MovieRepositoryImpl(movieService)
    }

    @Test
    fun `fetch movie list success test`() {
        val mockMovieListResponseDto = mockk<MovieListResponseDto>()
        val mockMovieListResponse = mockk<MovieListResponse>()
        val mockHttpMovieListResponseDto = mockk<Response<MovieListResponseDto>>()

        every {
            mockHttpMovieListResponseDto.isSuccessful
        } returns true

        every {
            mockHttpMovieListResponseDto.body()
        } returns mockMovieListResponseDto

        every {
            mockHttpMovieListResponseDto.body()?.mapToDomain()
        } returns mockMovieListResponse

        coEvery {
            movieService.getMoviesList()
        } returns mockHttpMovieListResponseDto

        runTest {
            val result = movieRepository.getMoviesList()
            assert(result is NetworkResponse.Success)
        }
    }

    @Test
    fun `fetch movie list error test`() {
        val mockHttpMovieListResponse = mockk<Response<MovieListResponseDto>>()
        every {
            mockHttpMovieListResponse.isSuccessful
        } returns false

        every {
            mockHttpMovieListResponse.message()
        } returns "Server not responding"

        coEvery {
            movieService.getMoviesList()
        } returns mockHttpMovieListResponse

        runTest {
            val result = movieRepository.getMoviesList()
            assert(result is NetworkResponse.Error)
        }
    }

    @Test
    fun `fetch movie list empty body failure`() {
        val mockHttpMovieListResponse = mockk<Response<MovieListResponseDto>>()
        every {
            mockHttpMovieListResponse.isSuccessful
        } returns true

        every {
            mockHttpMovieListResponse.body()
        } returns null

        coEvery {
            movieService.getMoviesList()
        } returns mockHttpMovieListResponse

        runTest {
            val result = movieRepository.getMoviesList()
            assert(result is NetworkResponse.Error)
        }
    }

    @Test
    fun `fetch movie list network failure`() {
        coEvery {
            movieService.getMoviesList()
        } throws UnknownHostException()

        runTest {
            val result = movieRepository.getMoviesList()
            assert(result is NetworkResponse.Exception)
        }
    }

    @Test
    fun `fetch movie detail success test`() {
        val mockMovieDetailResponseDto = mockk<MovieDetailResponseDto>()
        val mockMovieDetailResponse = mockk<MovieDetailResponse>()
        val mockHttpMovieDetailResponseDto = mockk<Response<MovieDetailResponseDto>>()

        every {
            mockHttpMovieDetailResponseDto.isSuccessful
        } returns true

        every {
            mockHttpMovieDetailResponseDto.body()
        } returns mockMovieDetailResponseDto

        every {
            mockHttpMovieDetailResponseDto.body()?.mapToDomain()
        } returns mockMovieDetailResponse

        coEvery {
            movieService.getMovieDetails("1")
        } returns mockHttpMovieDetailResponseDto

        runTest {
            val result = movieRepository.getMovieDetails("1")
            assert(result is NetworkResponse.Success)
        }
    }

    @Test
    fun `fetch movie detail error test`() {
        val mockHttpMovieDetailResponse = mockk<Response<MovieDetailResponseDto>>()
        every {
            mockHttpMovieDetailResponse.isSuccessful
        } returns false

        every {
            mockHttpMovieDetailResponse.message()
        } returns "Server not responding"

        coEvery {
            movieService.getMovieDetails("1")
        } returns mockHttpMovieDetailResponse

        runTest {
            val result = movieRepository.getMovieDetails("1")
            assert(result is NetworkResponse.Error)
        }
    }

    @Test
    fun `fetch movie detail empty body test failure`() {
        val mockHttpMovieListResponse = mockk<Response<MovieDetailResponseDto>>()
        every {
            mockHttpMovieListResponse.isSuccessful
        } returns true

        every {
            mockHttpMovieListResponse.body()
        } returns null

        coEvery {
            movieService.getMovieDetails("1")
        } returns mockHttpMovieListResponse

        runTest {
            val result = movieRepository.getMovieDetails("1")
            assert(result is NetworkResponse.Error)
        }
    }

    @Test
    fun `fetch movie detail network failure`() {
        coEvery {
            movieService.getMovieDetails("1")
        } throws UnknownHostException()

        runTest {
            val result = movieRepository.getMoviesList()
            assert(result is NetworkResponse.Exception)
        }
    }
}
