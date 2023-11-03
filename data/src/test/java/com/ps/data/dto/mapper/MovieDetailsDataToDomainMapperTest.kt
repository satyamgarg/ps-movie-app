package com.ps.data.dto.mapper

import com.ps.data.dto.GenreDto
import com.ps.data.dto.MovieDetailResponseDto
import com.ps.data.dto.ProductionCompanyDto
import com.ps.data.mapper.GenreDataToDomainMapper
import com.ps.data.mapper.MovieDetailsDataToDomainMapper
import com.ps.data.mapper.ProductionCompaniesDataToDomainMapper
import com.ps.domain.modal.GenreDomainModel
import com.ps.domain.modal.MovieDetailsDomainModel
import com.ps.domain.modal.ProductionCompanyDomainModel
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test

class MovieDetailsDataToDomainMapperTest {

    private val geneDataToDomainMapper = GenreDataToDomainMapper()
    private val productionCompaniesDataToDomainMapper = ProductionCompaniesDataToDomainMapper()
    private val movieDetailsDataToDomainMapper = MovieDetailsDataToDomainMapper(
        geneDataToDomainMapper,
        productionCompaniesDataToDomainMapper,
    )

    companion object {
        private const val ID = 10
        private const val TITLE = "title"
        private const val TITLE_OTHER = "title_other"
        private const val BACKDROP_PATH = "/path"
        private const val OVERVIEW = "overview"
        private const val POSTER_PATH = "poster_path"
        private const val VOTE_AVERAGE = 3.0
        private const val VOTE_COUNT = 5460L
        private const val GENRE_ID = 1
        private const val GENRE_NAME = "G1"
        private const val PRODUCTION_COMPANY_ID = 11
        private const val PRODUCTION_COMPANY_NAME = "name"
        private const val PRODUCTION_COMPANY_ORIGIN_COUNTRY = "us"
        private const val PRODUCTION_COMPANY_LOGO_PATH = "logo"
    }

    @Test
    fun `movie detail response dto to domain data model conversion success test`() {
        val movieDetailResponseDto = MovieDetailResponseDto(
            id = ID,
            title = TITLE,
            backdropPath = BACKDROP_PATH,
            genres = listOf(
                GenreDto(GENRE_ID, GENRE_NAME),
            ),
            overview = OVERVIEW,
            posterPath = POSTER_PATH,
            productionCompanies = listOf(
                ProductionCompanyDto(
                    id = PRODUCTION_COMPANY_ID,
                    logoPath = PRODUCTION_COMPANY_LOGO_PATH,
                    name = PRODUCTION_COMPANY_NAME,
                    originCountry = PRODUCTION_COMPANY_ORIGIN_COUNTRY,
                ),
            ),
            voteAverage = VOTE_AVERAGE,
            voteCount = VOTE_COUNT,
        )
        val movieDetailResponseDtoDomainMock =
            movieDetailsDataToDomainMapper.mapToDomain(movieDetailResponseDto)

        val movieDetailResponse = MovieDetailsDomainModel(
            id = ID,
            title = TITLE,
            backdropPath = BACKDROP_PATH,
            genres = listOf(
                GenreDomainModel(GENRE_ID, GENRE_NAME),
            ),
            overview = OVERVIEW,
            posterPath = POSTER_PATH,
            productionCompanies = listOf(
                ProductionCompanyDomainModel(
                    name = PRODUCTION_COMPANY_NAME,
                    id = PRODUCTION_COMPANY_ID,
                ),
            ),
            voteAverage = VOTE_AVERAGE,
            voteCount = VOTE_COUNT,
        )

        assertTrue(movieDetailResponseDtoDomainMock == movieDetailResponse)
        assertTrue(movieDetailResponseDtoDomainMock.hashCode() == movieDetailResponse.hashCode())
    }

    @Test
    fun `movie detail response dto to domain data model conversion failure test`() {
        val movieDetailResponseDto = MovieDetailResponseDto(
            id = ID,
            title = TITLE,
            backdropPath = BACKDROP_PATH,
            genres = listOf(
                GenreDto(GENRE_ID, GENRE_NAME),
            ),
            overview = OVERVIEW,
            posterPath = POSTER_PATH,
            productionCompanies = listOf(
                ProductionCompanyDto(
                    id = PRODUCTION_COMPANY_ID,
                    logoPath = PRODUCTION_COMPANY_LOGO_PATH,
                    name = PRODUCTION_COMPANY_NAME,
                    originCountry = PRODUCTION_COMPANY_ORIGIN_COUNTRY,
                ),
            ),
            voteAverage = VOTE_AVERAGE,
            voteCount = VOTE_COUNT,
        )
        val movieDetailResponseDtoDomain =
            movieDetailsDataToDomainMapper.mapToDomain(movieDetailResponseDto)

        val movieDetailResponse = MovieDetailsDomainModel(
            id = ID,
            title = TITLE_OTHER,
            backdropPath = BACKDROP_PATH,
            genres = listOf(
                GenreDomainModel(GENRE_ID, GENRE_NAME),
            ),
            overview = OVERVIEW,
            posterPath = POSTER_PATH,
            productionCompanies = listOf(
                ProductionCompanyDomainModel(
                    id = PRODUCTION_COMPANY_ID,
                    name = PRODUCTION_COMPANY_NAME,
                ),
            ),
            voteAverage = VOTE_AVERAGE,
            voteCount = VOTE_COUNT,
        )

        assertFalse(movieDetailResponseDtoDomain == movieDetailResponse)
        assertFalse(
            movieDetailResponseDtoDomain.hashCode() ==
                movieDetailResponse.hashCode(),
        )
    }
}
