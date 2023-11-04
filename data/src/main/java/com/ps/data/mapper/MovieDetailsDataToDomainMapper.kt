package com.ps.data.mapper

import com.ps.data.dto.MovieDetailResponseDto
import com.ps.domain.modal.MovieDetailsDomainModel
import javax.inject.Inject

class MovieDetailsDataToDomainMapper @Inject constructor(
    private val genreDataToDomainMapper: GenreDataToDomainMapper,
    private val productionCompaniesDataToDomainMapper: ProductionCompaniesDataToDomainMapper,
) {
    operator fun invoke(dataModel: MovieDetailResponseDto): MovieDetailsDomainModel {
        return MovieDetailsDomainModel(
            id = dataModel.id,
            title = dataModel.title,
            overview = dataModel.overview,
            backdropPath = dataModel.backdropPath,
            voteAverage = dataModel.voteAverage,
            voteCount = dataModel.voteCount,
            genres = dataModel.genres.map { genreDataToDomainMapper(it) },
            productionCompanies = dataModel.productionCompanies.map {
                productionCompaniesDataToDomainMapper(
                    it,
                )
            },
        )
    }
}
