package com.ps.data.mapper

import com.ps.data.dto.GenreDto
import com.ps.data.utils.Constants
import com.ps.domain.mapper.Mapper
import com.ps.domain.modal.GenreDomainModel
import javax.inject.Inject

class GenreDataToDomainMapper @Inject constructor() : Mapper<
    GenreDto,
    GenreDomainModel,
    > {

    override fun mapToDomain(dataModel: GenreDto): GenreDomainModel {
        return GenreDomainModel(dataModel.id ?: 0, dataModel.name ?: Constants.EMPTY_STRING)
    }
}
