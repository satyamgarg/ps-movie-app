package com.ps.data.dto

import com.ps.domain.modal.ProductionCompany
import com.ps.domain.utils.Mapper
import com.squareup.moshi.Json

data class ProductionCompanyDto(
    @Json(name = "id")
    val id: Int?,

    @Json(name = "logo_path")
    val logoPath: String?,

    @Json(name = "name")
    val name: String?,

    @Json(name = "origin_country")
    val originCountry: String?,

) : Mapper<ProductionCompany> {
    override fun mapToDomain(): ProductionCompany = ProductionCompany(id, logoPath, name, originCountry)
}
