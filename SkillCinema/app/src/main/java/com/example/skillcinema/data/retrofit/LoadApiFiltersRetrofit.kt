package com.example.skillcinema.data.retrofit

import com.example.skillcinema.data.KINOPOISK_API_BASE_URL
import com.example.skillcinema.data.api.LoadApiFiltersApi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object LoadApiFiltersRetrofit {
    private val retrofit = Retrofit.Builder()
        .baseUrl(KINOPOISK_API_BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
    val loadApiFiltersApi: LoadApiFiltersApi = retrofit.create(
        LoadApiFiltersApi::class.java
    )
}