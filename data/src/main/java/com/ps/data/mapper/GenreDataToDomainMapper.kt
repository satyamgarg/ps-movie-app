package com.ps.data.mapper

import com.ps.data.dto.GenreDto
import com.ps.domain.modal.GenreDomainModel
import javax.inject.Inject

class GenreDataToDomainMapper @Inject constructor() {

    operator fun invoke(dataModel: GenreDto): GenreDomainModel {
        return GenreDomainModel(dataModel.id, dataModel.name)
    }
}
