package com.ps.data.dto.mapper

import com.ps.data.dto.MovieListResponseDto
import com.ps.data.dto.MovieResultDto
import com.ps.data.mapper.MovieListDataToDomainMapper
import com.ps.data.mapper.MovieResultDataToDomainMapper
import com.ps.domain.modal.MovieListDomainModel
import com.ps.domain.modal.MovieResultDomainModel
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test

class MovieListDomainModelMapperTest {

    private val movieResultDataToDomainMapper = MovieResultDataToDomainMapper()
    private val movieListDataToDomainMapper = MovieListDataToDomainMapper(movieResultDataToDomainMapper)

    companion object {
        private const val MOVIE_LIST_ID = 10
        private const val MOVIE_LIST_ID_NEXT = 11
        private const val MOVIE_LIST_TITLE = "title"
        private const val MOVIE_LIST_POSTER_PATH = "/posterpath"
        private const val MOVIE_LIST_BACKDROP_PATH = "/backdroppath"
        private const val MOVIE_LIST_OVERVIEW = "overview"
        private const val MOVIE_LIST_RELEASE_DATE = "date"
        private const val MOVIE_LIST_POPULARITY = 10.0
        private const val MOVIE_LIST_VOTE_COUNT = 10
        private const val MOVIE_LIST_VOTE_AVERAGE = 10.0
        private const val MOVIE_LIST_VIDEO = false
        private const val MOVIE_LIST_ORIGINAL_LANGUAGE = ""
        private const val MOVIE_LIST_ORIGINAL_TITLE = ""
        private const val MOVIE_LIST_ADULT = true
    }

    @Test
    fun `movie list response dto to domain data conversion success test`() {
        val movieListResponseDto = MovieListResponseDto(
            results = listOf(
                MovieResultDto(
                    adult = MOVIE_LIST_ADULT,
                    id = MOVIE_LIST_ID,
                    overview = MOVIE_LIST_OVERVIEW,
                    title = MOVIE_LIST_TITLE,
                    posterPath = MOVIE_LIST_POSTER_PATH,
                    backdropPath = MOVIE_LIST_BACKDROP_PATH,
                    releaseDate = MOVIE_LIST_RELEASE_DATE,
                    voteAverage = MOVIE_LIST_VOTE_AVERAGE,
                    voteCount = MOVIE_LIST_VOTE_COUNT,
                    genreIds = emptyList(),
                    originalLanguage = MOVIE_LIST_ORIGINAL_LANGUAGE,
                    originalTitle = MOVIE_LIST_ORIGINAL_TITLE,
                    popularity = MOVIE_LIST_POPULARITY,
                    video = MOVIE_LIST_VIDEO,
                ),
            ),
        )
        val movieListResponseDtoDomainMock = movieListDataToDomainMapper.mapToDomain(movieListResponseDto)

        val movieListDomainModelResponse = MovieListDomainModel(
            results = listOf(
                MovieResultDomainModel(
                    id = MOVIE_LIST_ID,
                    title = MOVIE_LIST_TITLE,
                    posterPath = MOVIE_LIST_POSTER_PATH,
                ),
            ),
        )

        assertTrue(movieListResponseDtoDomainMock == movieListDomainModelResponse)
        assertTrue(movieListResponseDtoDomainMock.hashCode() == movieListDomainModelResponse.hashCode())
    }

    @Test
    fun `movie list response dto to domain data conversion failure test`() {
        val movieListResponseDto = MovieListResponseDto(
            results = listOf(
                MovieResultDto(
                    adult = MOVIE_LIST_ADULT,
                    id = MOVIE_LIST_ID,
                    overview = MOVIE_LIST_OVERVIEW,
                    title = MOVIE_LIST_TITLE,
                    posterPath = MOVIE_LIST_POSTER_PATH,
                    backdropPath = MOVIE_LIST_BACKDROP_PATH,
                    releaseDate = MOVIE_LIST_RELEASE_DATE,
                    voteAverage = MOVIE_LIST_VOTE_AVERAGE,
                    voteCount = MOVIE_LIST_VOTE_COUNT,
                    genreIds = emptyList(),
                    originalLanguage = MOVIE_LIST_ORIGINAL_LANGUAGE,
                    originalTitle = MOVIE_LIST_ORIGINAL_TITLE,
                    popularity = MOVIE_LIST_POPULARITY,
                    video = MOVIE_LIST_VIDEO,
                ),
            ),
        )
        val movieListDomainModelResponse = MovieListDomainModel(
            results = listOf(
                MovieResultDomainModel(
                    id = MOVIE_LIST_ID,
                    title = MOVIE_LIST_TITLE,
                    posterPath = MOVIE_LIST_POSTER_PATH,
                ),
                MovieResultDomainModel(
                    id = MOVIE_LIST_ID_NEXT,
                    title = MOVIE_LIST_TITLE,
                    posterPath = MOVIE_LIST_POSTER_PATH,
                ),
            ),
        )
        val movieListResponseDtoDomain = movieListDataToDomainMapper.mapToDomain(movieListResponseDto)

        assertFalse(movieListResponseDtoDomain == movieListDomainModelResponse)
        assertFalse(
            movieListResponseDtoDomain.hashCode() ==
                movieListDomainModelResponse.hashCode(),
        )
    }
}
