package com.ps.data.dto

import com.ps.domain.modal.ProductionCountry
import com.ps.domain.utils.Mapper
import com.squareup.moshi.Json

data class ProductionCountryDto(

    @Json(name = "iso_3166_1")
    val iso31661: String?,

    @Json(name = "name")
    val name: String?,

) : Mapper<ProductionCountry> {
    override fun mapToDomain(): ProductionCountry = ProductionCountry(iso31661, name)
}
