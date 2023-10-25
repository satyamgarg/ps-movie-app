package com.ps.domain.modal

data class MovieDetailResponse(
    val id: Int? = null,
    val title: String? = null,
    val backdropPath: String? = null,
    val genres: List<Genre?>? = null,
    val overview: String? = null,
    val posterPath: String? = null,
    val productionCompanies: List<ProductionCompany?>? = null,
    val voteAverage: Double? = null,
    val voteCount: Long? = null,
)
