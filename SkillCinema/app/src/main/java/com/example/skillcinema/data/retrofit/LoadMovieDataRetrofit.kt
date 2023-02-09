package com.example.skillcinema.data.retrofit

import com.example.skillcinema.data.KINOPOISK_API_BASE_URL
import com.example.skillcinema.data.api.LoadMovieDataApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object LoadMovieDataRetrofit {
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    private val retrofit = Retrofit.Builder()
        .baseUrl(KINOPOISK_API_BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
    val loadMovieDataApi: LoadMovieDataApi = retrofit.create(
        LoadMovieDataApi::class.java
    )
}