package com.ps.domain.modal

data class MovieListResponse(
    val page: Int? = 0,
    var results: List<MovieResult>?,
    val totalPages: Int? = 0,
    val totalResults: Int? = 0,
)
