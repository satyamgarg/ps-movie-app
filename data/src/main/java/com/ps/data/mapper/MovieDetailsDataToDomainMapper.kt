package com.ps.data.mapper

import com.ps.data.dto.MovieDetailResponseDto
import com.ps.data.utils.Constants
import com.ps.domain.mapper.Mapper
import com.ps.domain.modal.MovieDetailsDomainModel
import javax.inject.Inject

class MovieDetailsDataToDomainMapper @Inject constructor(
    private val genreDataToDomainMapper: GenreDataToDomainMapper,
    private val productionCompaniesDataToDomainMapper: ProductionCompaniesDataToDomainMapper,
) : Mapper<MovieDetailResponseDto, MovieDetailsDomainModel> {
    override fun mapToDomain(dataModel: MovieDetailResponseDto): MovieDetailsDomainModel {
        return MovieDetailsDomainModel(
            id = dataModel.id ?: 0,
            title = dataModel.title ?: Constants.EMPTY_STRING,
            overview = dataModel.overview ?: Constants.EMPTY_STRING,
            posterPath = dataModel.posterPath ?: Constants.EMPTY_STRING,
            backdropPath = dataModel.backdropPath ?: Constants.EMPTY_STRING,
            voteAverage = dataModel.voteAverage ?: 0.0,
            voteCount = dataModel.voteCount ?: 0,
            genres = if (dataModel.genres.isNullOrEmpty()) {
                emptyList()
            } else {
                dataModel.genres.map { genreDataToDomainMapper.mapToDomain(it!!) }
            },
            productionCompanies = if (dataModel.productionCompanies.isNullOrEmpty()) {
                emptyList()
            } else {
                dataModel.productionCompanies.map { productionCompaniesDataToDomainMapper.mapToDomain(it!!) }
            },
        )
    }
}
