package com.ps.data.dto

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class MovieListResponseDto(
    @Json(name = "page")
    val page: Int? = 0,

    @Json(name = "results")
    val results: List<MovieResultDto>?,

    @Json(name = "total_pages")
    val totalPages: Int? = 0,

    @Json(name = "total_results")
    val totalResults: Int? = 0,

)
