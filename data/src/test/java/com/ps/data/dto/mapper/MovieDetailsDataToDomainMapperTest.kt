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
import io.mockk.mockk
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

    @Test
    fun `movie detail response  test conversion  dto to domain data model success test`() {
        val movieDetailResponseDto = MovieDetailResponseDto(
            adult = ADULT,
            backdropPath = BACKDROP_PATH,
            belongsToCollection = mockk(),
            budget = BUDGET,
            genres = listOf(GenreDto(GENRE_ID, GENRE_NAME)),
            homepage = HOME_PAGE,
            id = ID,
            imdbId = IMDB_ID,
            originalLanguage = ORIGINAL_LANGUAGE,
            originalTitle = ORIGINAL_TITLE,
            overview = OVERVIEW,
            popularity = POPULARITY,
            posterPath = POSTER_PATH,
            productionCompanies = listOf(
                ProductionCompanyDto(
                    id = PRODUCTION_COMPANY_ID,
                    logoPath = PRODUCTION_COMPANY_LOGO_PATH,
                    name = PRODUCTION_COMPANY_NAME,
                    originCountry = PRODUCTION_COMPANY_ORIGIN_COUNTRY,
                ),
            ),
            productionCountries = listOf(),
            releaseDate = RELEASE_DATE,
            revenue = REVENUE,
            runtime = RUNTIME,
            spokenLanguages = listOf(),
            status = STATUS,
            tagline = TAGLINE,
            title = TITLE,
            video = VIDEO,
            voteAverage = VOTE_AVERAGE,
            voteCount = VOTE_COUNT,
        )

        val movieDetailResponseDtoDomainMock =
            movieDetailsDataToDomainMapper(movieDetailResponseDto)

        val movieDetailResponse = MovieDetailsDomainModel(
            id = ID,
            title = TITLE,
            backdropPath = BACKDROP_PATH,
            genres = listOf(
                GenreDomainModel(GENRE_ID, GENRE_NAME),
            ),
            overview = OVERVIEW,
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
    fun `movie detail response test conversion failure when title cannot be converted`() {
        val movieDetailResponseDto = MovieDetailResponseDto(
            adult = ADULT,
            backdropPath = BACKDROP_PATH,
            belongsToCollection = mockk(),
            budget = BUDGET,
            genres = listOf(GenreDto(GENRE_ID, GENRE_NAME)),
            homepage = HOME_PAGE,
            id = ID,
            imdbId = IMDB_ID,
            originalLanguage = ORIGINAL_LANGUAGE,
            originalTitle = ORIGINAL_TITLE,
            overview = OVERVIEW,
            popularity = POPULARITY,
            posterPath = POSTER_PATH,
            productionCompanies = listOf(
                ProductionCompanyDto(
                    id = PRODUCTION_COMPANY_ID,
                    logoPath = PRODUCTION_COMPANY_LOGO_PATH,
                    name = PRODUCTION_COMPANY_NAME,
                    originCountry = PRODUCTION_COMPANY_ORIGIN_COUNTRY,
                ),
            ),
            productionCountries = listOf(),
            releaseDate = RELEASE_DATE,
            revenue = REVENUE,
            runtime = RUNTIME,
            spokenLanguages = listOf(),
            status = STATUS,
            tagline = TAGLINE,
            title = TITLE,
            video = VIDEO,
            voteAverage = VOTE_AVERAGE,
            voteCount = VOTE_COUNT,
        )
        val movieDetailResponseDtoDomain =
            movieDetailsDataToDomainMapper(movieDetailResponseDto)

        val movieDetailResponse = MovieDetailsDomainModel(
            id = ID,
            title = TITLE_OTHER,
            backdropPath = BACKDROP_PATH,
            genres = listOf(
                GenreDomainModel(GENRE_ID, GENRE_NAME),
            ),
            overview = OVERVIEW,
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

    private companion object {
        const val ADULT = false
        const val BACKDROP_PATH = "backdrop_path"
        const val BUDGET = 123L
        const val GENRE_ID = 1
        const val GENRE_NAME = "genre_name"
        const val HOME_PAGE = "home_page"
        const val ID = 123
        const val IMDB_ID = "imdb_id"
        const val ORIGINAL_LANGUAGE = "original_language"
        const val ORIGINAL_TITLE = "original_title"
        const val OVERVIEW = "overview"
        const val POPULARITY = 123.1
        const val POSTER_PATH = "poster_path"
        const val PRODUCTION_COMPANY_ID = 1
        const val PRODUCTION_COMPANY_LOGO_PATH = "production_company_logo_path"
        const val PRODUCTION_COMPANY_NAME = "production_company_name"
        const val PRODUCTION_COMPANY_ORIGIN_COUNTRY = "production_company_origin_country"
        const val RELEASE_DATE = "release_date"
        const val REVENUE = 123L
        const val RUNTIME = 123
        const val STATUS = "status"
        const val TAGLINE = "tagline"
        const val TITLE = "title"
        const val TITLE_OTHER = "title_other"
        const val VIDEO = false
        const val VOTE_AVERAGE = 2.3
        const val VOTE_COUNT = 123L
    }
}
