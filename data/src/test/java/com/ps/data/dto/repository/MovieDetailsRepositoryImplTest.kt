package com.ps.data.dto.repository

import com.ps.data.dto.MovieDetailResponseDto
import com.ps.data.mapper.MovieDetailsDataToDomainMapper
import com.ps.data.remote.MovieService
import com.ps.data.repository.MovieDetailRepositoryImpl
import com.ps.domain.modal.MovieDetailsDomainModel
import com.ps.domain.repository.MovieDetailRepository
import com.ps.domain.utils.Result
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
class MovieDetailsRepositoryImplTest {

    private lateinit var movieDetailRepository: MovieDetailRepository
    private val movieService = mockk<MovieService>()

    private val mockkMovieDetailsDataToDomainMapper = mockk<MovieDetailsDataToDomainMapper>()

    @Before
    fun setUp() {
        movieDetailRepository =
            MovieDetailRepositoryImpl(movieService, mockkMovieDetailsDataToDomainMapper)
    }

    @Test
    fun `fetch movie detail success test`() {
        val mockHttpMovieDetailResponseDto = mockk<Response<MovieDetailResponseDto>>()
        val mockMovieDetailResponseDto = mockk<MovieDetailResponseDto>()
        val mockMovieDetailResponse = mockk<MovieDetailsDomainModel>()

        every {
            mockHttpMovieDetailResponseDto.isSuccessful
        } returns true

        every {
            mockHttpMovieDetailResponseDto.body()
        } returns mockMovieDetailResponseDto

        every {
            mockkMovieDetailsDataToDomainMapper(mockHttpMovieDetailResponseDto.body()!!)
        } returns mockMovieDetailResponse

        coEvery {
            movieService.getMovieDetails(MOVIE_ID)
        } returns mockHttpMovieDetailResponseDto

        runTest {
            val result = movieDetailRepository.getMovieDetails(MOVIE_ID)
            assert(result is Result.Success)
        }
    }

    @Test
    fun `fetch movie detail api error failure test`() {
        val mockHttpMovieDetailResponse = mockk<Response<MovieDetailResponseDto>>()
        every {
            mockHttpMovieDetailResponse.isSuccessful
        } returns false

        every {
            mockHttpMovieDetailResponse.message()
        } returns SERVER_ERROR

        coEvery {
            movieService.getMovieDetails(MOVIE_ID)
        } returns mockHttpMovieDetailResponse

        runTest {
            val result = movieDetailRepository.getMovieDetails(MOVIE_ID)
            assert(result is Result.Error)
        }
    }

    @Test
    fun `fetch movie detail empty body failure test`() {
        val mockHttpMovieListResponse = mockk<Response<MovieDetailResponseDto>>()
        every {
            mockHttpMovieListResponse.isSuccessful
        } returns true

        every {
            mockHttpMovieListResponse.body()
        } returns null

        coEvery {
            movieService.getMovieDetails(MOVIE_ID)
        } returns mockHttpMovieListResponse

        runTest {
            val result = movieDetailRepository.getMovieDetails(MOVIE_ID)
            assert(result is Result.Error)
        }
    }

    @Test
    fun `fetch movie detail network throws exception failure test`() {
        coEvery {
            movieService.getMovieDetails(MOVIE_ID)
        } throws UnknownHostException()

        runTest {
            val result = movieDetailRepository.getMovieDetails(MOVIE_ID)
            assert(result is Result.Exception)
        }
    }

    private companion object {
        const val MOVIE_ID = "1"
        const val SERVER_ERROR = "Server not responding"
    }
}
