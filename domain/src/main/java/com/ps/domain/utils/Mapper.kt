package com.ps.domain.utils

interface Mapper<T> {
    fun mapToDomain(): T
}
