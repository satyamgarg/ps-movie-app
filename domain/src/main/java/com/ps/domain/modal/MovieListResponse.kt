package com.ps.domain.modal

data class MovieListResponse(
    var results: List<MovieResult> = emptyList(),
)
