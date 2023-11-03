package com.ps.data.dto

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class MovieDetailResponseDto(

    @field:Json(name = "adult")
    val adult: Boolean? = null,

    @Json(name = "backdrop_path")
    val backdropPath: String? = null,

    @field:Json(name = "belongs_to_collection")
    val belongsToCollection: BelongsToCollectionDto? = null,

    @field:Json(name = "budget")
    val budget: Long? = null,

    @field:Json(name = "genreDomainModels")
    val genres: List<GenreDto?>? = null,

    @field:Json(name = "homepage")
    val homepage: String? = null,

    @field:Json(name = "id")
    val id: Int? = null,

    @field:Json(name = "imdb_id")
    val imdbId: String? = null,

    @field:Json(name = "original_language")
    val originalLanguage: String? = null,

    @field:Json(name = "original_title")
    val originalTitle: String? = null,

    @field:Json(name = "overview")
    val overview: String? = null,

    @field:Json(name = "popularity")
    val popularity: Double? = null,

    @field:Json(name = "poster_path")
    val posterPath: String? = null,

    @Json(name = "production_companies")
    val productionCompanies: List<ProductionCompanyDto?>? = null,

    @field:Json(name = "production_countries")
    val productionCountries: List<ProductionCountryDto?>? = null,

    @field:Json(name = "release_date")
    val releaseDate: String? = null,

    @field:Json(name = "revenue")
    val revenue: Long? = null,

    @field:Json(name = "runtime")
    val runtime: Int? = null,

    @field:Json(name = "spoken_languages")
    val spokenLanguages: List<SpokenLanguageDto?>? = null,

    @field:Json(name = "status")
    val status: String? = null,

    @field:Json(name = "tagline")
    val tagline: String? = null,

    @field:Json(name = "tagline")
    val title: String? = null,

    @field:Json(name = "video")
    val video: Boolean? = null,

    @Json(name = "vote_average")
    val voteAverage: Double? = null,

    @Json(name = "vote_count")
    val voteCount: Long? = null,

)
