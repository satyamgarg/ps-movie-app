package com.ps.domain.mapper

interface Mapper<T> {
    fun mapToDomain(): T
}
