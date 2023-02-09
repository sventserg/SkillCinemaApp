package com.example.skillcinema.data.retrofit

import com.example.skillcinema.data.KINOPOISK_API_BASE_URL
import com.example.skillcinema.data.api.LoadSeasonsApi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object LoadSeasonsRetrofit {
    private val retrofit = Retrofit.Builder()
        .baseUrl(KINOPOISK_API_BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
    val loadSeasonsApi: LoadSeasonsApi = retrofit.create(
        LoadSeasonsApi::class.java
    )
}