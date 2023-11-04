package com.ps.data.mapper

import com.ps.data.dto.MovieListResponseDto
import com.ps.domain.modal.MovieListDomainModel
import javax.inject.Inject

class MovieListDataToDomainMapper @Inject constructor(
    private val movieResultDataToDomainMapper: MovieResultDataToDomainMapper,
) {
    operator fun invoke(dataModel: MovieListResponseDto): MovieListDomainModel {
        return MovieListDomainModel(
            results = dataModel.results.map { movieResultDataToDomainMapper.invoke(it) },
        )
    }
}
