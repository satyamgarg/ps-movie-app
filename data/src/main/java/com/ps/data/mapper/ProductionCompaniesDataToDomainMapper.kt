package com.ps.data.mapper

import com.ps.data.dto.ProductionCompanyDto
import com.ps.data.utils.Constants
import com.ps.domain.mapper.Mapper
import com.ps.domain.modal.ProductionCompanyDomainModel
import javax.inject.Inject

class ProductionCompaniesDataToDomainMapper @Inject constructor() :
    Mapper<ProductionCompanyDto, ProductionCompanyDomainModel> {
    override fun mapToDomain(dataModel: ProductionCompanyDto): ProductionCompanyDomainModel {
        return ProductionCompanyDomainModel(
            id = dataModel.id ?: 0,
            name = dataModel.name ?: Constants.EMPTY_STRING,
        )
    }
}
