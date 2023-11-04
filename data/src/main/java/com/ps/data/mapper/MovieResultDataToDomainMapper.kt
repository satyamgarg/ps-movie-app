package com.ps.data.mapper

import com.ps.data.dto.MovieResultDto
import com.ps.domain.modal.MovieResultDomainModel
import javax.inject.Inject

class MovieResultDataToDomainMapper @Inject constructor() {
    operator fun invoke(dataModel: MovieResultDto): MovieResultDomainModel {
        return MovieResultDomainModel(
            id = dataModel.id,
            posterPath = dataModel.posterPath,
            title = dataModel.title,
        )
    }
}
