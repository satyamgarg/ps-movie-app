package com.ps.data.dto

import com.ps.domain.mapper.Mapper
import com.ps.domain.modal.Genre
import com.squareup.moshi.Json

data class GenreDto(

    @Json(name = "id")
    val id: Int?,

    @Json(name = "name")
    val name: String?,

) : Mapper<Genre> {
    override fun mapToDomain(): Genre = Genre(id, name)
}
