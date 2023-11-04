package com.ps.data.dto

import com.squareup.moshi.Json

data class BelongsToCollectionDto(

    @Json(name = "id")
    val id: Int,

    @Json(name = "name")
    val name: String,

    @Json(name = "poster_path")
    val posterPath: String?,

    @Json(name = "backdrop_path")
    val backdropPath: String?,
)
