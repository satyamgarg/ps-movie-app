package com.ps.movie.util

fun Double?.safeDouble(): Double {
    return this ?: 0.0
}

fun Long?.safeLong(): Long {
    return this ?: 0
}
