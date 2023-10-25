package com.ps.movie.util

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.lang.reflect.Type

class MoshiParser {

    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    fun <T> fromJson(json: String, type: Type): T? {
        val jsonAdapter: JsonAdapter<T> = moshi.adapter(type)
        return jsonAdapter.fromJson(json)
    }

    fun <T> toJson(obj: T, type: Type): String? {
        val jsonAdapter: JsonAdapter<T> = moshi.adapter(type)
        return jsonAdapter.toJson(obj)
    }

}