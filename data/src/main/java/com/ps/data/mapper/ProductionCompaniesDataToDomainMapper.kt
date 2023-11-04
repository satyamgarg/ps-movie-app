package com.ps.data.mapper

import com.ps.data.dto.ProductionCompanyDto
import com.ps.domain.modal.ProductionCompanyDomainModel
import javax.inject.Inject

class ProductionCompaniesDataToDomainMapper @Inject constructor() {
    operator fun invoke(dataModel: ProductionCompanyDto): ProductionCompanyDomainModel {
        return ProductionCompanyDomainModel(
            id = dataModel.id,
            name = dataModel.name,
        )
    }
}
