package com.ps.data.mapper

import com.ps.data.dto.MovieResultDto
import com.ps.data.utils.Constants
import com.ps.domain.mapper.Mapper
import com.ps.domain.modal.MovieResultDomainModel
import javax.inject.Inject

class MovieResultDataToDomainMapper @Inject constructor() :
    Mapper<MovieResultDto, MovieResultDomainModel> {
    override fun mapToDomain(dataModel: MovieResultDto): MovieResultDomainModel {
        return MovieResultDomainModel(
            id = dataModel.id ?: 0,
            posterPath = dataModel.posterPath ?: Constants.EMPTY_STRING,
            title = dataModel.title ?: Constants.EMPTY_STRING,
        )
    }
}
