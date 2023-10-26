package com.ps.data.dto.mapper

import com.ps.data.dto.MovieListResponseDto
import com.ps.data.dto.MovieResultDto
import com.ps.domain.modal.MovieListResponse
import com.ps.domain.modal.MovieResult
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test

class MovieListMapperTest {

    @Test
    fun `movie list response dto to domain data conversion success test`() {
        val mapper = MovieListResponseDto(
            results = listOf(
                MovieResultDto(
                    adult = true,
                    id = 10,
                    overview = "overview",
                    title = "title",
                    posterPath = "/posterpath",
                    backdropPath = "/backdroppath",
                    releaseDate = "date",
                    voteAverage = 10.0,
                    voteCount = 10,
                    genreIds = emptyList(),
                    originalLanguage = "",
                    originalTitle = "",
                    popularity = 10.0,
                    video = false,
                ),
            ),
        )
        val movieListResponseDtoDomain = mapper.mapToDomain()
        val movieListResponse = MovieListResponse(
            results = listOf(
                MovieResult(
                    id = 10,
                    overview = "overview",
                    title = "title",
                    posterPath = "/posterpath",
                    releaseDate = "date",
                ),
            ),
        )
        assertTrue(movieListResponseDtoDomain == movieListResponse)
        assertTrue(movieListResponseDtoDomain.hashCode() == movieListResponse.hashCode())
    }

    @Test
    fun `movie list response dto to domain data conversion failure test`() {
        val mapper = MovieListResponseDto(
            results = listOf(
                MovieResultDto(
                    adult = true,
                    id = 10,
                    overview = "overview",
                    title = "title",
                    posterPath = "/posterpath",
                    backdropPath = "/backdroppath",
                    releaseDate = "date",
                    voteAverage = 10.0,
                    voteCount = 10,
                    genreIds = emptyList(),
                    originalLanguage = "",
                    originalTitle = "",
                    popularity = 10.0,
                    video = false,
                ),
            ),
        )
        val movieListResponseDtoDomain = mapper.mapToDomain()
        val movieListResponse = MovieListResponse(
            results = listOf(
                MovieResult(
                    id = 10,
                    overview = "overview detail",
                    title = "title name",
                    posterPath = "/poster path",
                    releaseDate = "26/10/13",
                ),
                MovieResult(
                    id = 11,
                    overview = "overview details",
                    title = "movie title name",
                    posterPath = "/poster_path_detail",
                    releaseDate = "26/10/13",
                ),
            ),
        )
        assertFalse(movieListResponseDtoDomain == movieListResponse)
        assertFalse(
            movieListResponseDtoDomain.hashCode() ==
                movieListResponse.hashCode(),
        )
    }
}
