package com.ps.data.dto

import com.ps.domain.mapper.Mapper
import com.ps.domain.modal.BelongsToCollection
import com.squareup.moshi.Json

data class BelongsToCollectionDto(
    val backdropPath: String?,

    @Json(name = "id")
    val id: Int?,

    @Json(name = "name")
    val name: String?,

    @Json(name = "poster_path")
    val posterPath: String?,

) : Mapper<BelongsToCollection> {
    override fun mapToDomain(): BelongsToCollection =
        BelongsToCollection(backdropPath, id, name, posterPath)
}
