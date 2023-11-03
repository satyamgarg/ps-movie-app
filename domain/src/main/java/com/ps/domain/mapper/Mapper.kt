package com.ps.domain.mapper

interface Mapper<D, T> {
    fun mapToDomain(dataModel: D): T
}
