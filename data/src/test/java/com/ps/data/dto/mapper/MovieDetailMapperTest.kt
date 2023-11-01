package com.ps.data.dto.mapper

import com.ps.data.dto.GenreDto
import com.ps.data.dto.MovieDetailResponseDto
import com.ps.data.dto.ProductionCompanyDto
import com.ps.data.utils.Constants
import com.ps.domain.modal.Genre
import com.ps.domain.modal.MovieDetailResponse
import com.ps.domain.modal.ProductionCompany
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test

class MovieDetailMapperTest {

    @Before
    fun setUp() {
    }

    @Test
    fun `movie detail response dto to domain data conversion success test`() {
        val movieDetailResponseDto = MovieDetailResponseDto(
            id = 10,
            title = null,
            backdropPath = "/path",
            genres = listOf(
                GenreDto(1, "G1"),
            ),
            overview = "overview",
            posterPath = "poster_path",
            productionCompanies = listOf(
                ProductionCompanyDto(
                    id = 11,
                    logoPath = "logo",
                    name = "name",
                    originCountry = "us",
                ),
            ),
        )
        val movieDetailResponseDtoDomain = movieDetailResponseDto.mapToDomain()

        val movieDetailResponse = MovieDetailResponse(
            id = 10,
            title = Constants.EMPTY_STRING,
            backdropPath = "/path",
            genres = listOf(
                Genre(1, "G1"),
            ),
            overview = "overview",
            posterPath = "poster_path",
            productionCompanies = listOf(
                ProductionCompany(id = 11, logoPath = "logo", name = "name", originCountry = "us"),
            ),
        )

        assertTrue(movieDetailResponseDtoDomain == movieDetailResponse)
        assertTrue(movieDetailResponseDtoDomain.hashCode() == movieDetailResponse.hashCode())
    }

    @Test
    fun `movie detail response dto to domain data conversion failure test`() {
        val mapper = MovieDetailResponseDto(
            id = 10,
            title = null,
            backdropPath = "/path",
            genres = listOf(
                GenreDto(1, "G1"),
                GenreDto(2, "G12"),
            ),
            overview = "overview",
            posterPath = "poster_path",
            productionCompanies = listOf(
                ProductionCompanyDto(
                    id = 11,
                    logoPath = "logo",
                    name = "name",
                    originCountry = "us",
                ),
                ProductionCompanyDto(
                    id = 12,
                    logoPath = "logo12",
                    name = "name12",
                    originCountry = "us",
                ),
            ),
        )
        val movieDetailResponseDtoDomain = mapper.mapToDomain()

        val movieDetailResponse = MovieDetailResponse(
            id = 100,
            title = "XXX",
            backdropPath = "/backdropPath",
            genres = listOf(
                Genre(11, "Genre1"),
            ),
            overview = "overview details",
            posterPath = "/poster_path",
            productionCompanies = listOf(
                ProductionCompany(
                    id = 21,
                    logoPath = "/logoPath",
                    name = "name1",
                    originCountry = "usa",
                ),
            ),
        )

        assertFalse(movieDetailResponseDtoDomain == movieDetailResponse)
        assertFalse(
            movieDetailResponseDtoDomain.hashCode() ==
                movieDetailResponse.hashCode(),
        )
    }
}
