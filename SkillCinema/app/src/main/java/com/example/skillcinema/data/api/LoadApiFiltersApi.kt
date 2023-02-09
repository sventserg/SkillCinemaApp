package com.example.skillcinema.data.api

import com.example.skillcinema.data.KINOPOISK_API_KEY
import com.example.skillcinema.data.GET_INFO_PATH
import com.example.skillcinema.data.dto.ApiFiltersDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.GET
import retrofit2.http.Headers

interface LoadApiFiltersApi {
    @Headers(KINOPOISK_API_KEY)
    @GET(GET_INFO_PATH)
    fun loadApiFilters(): Call<ApiFiltersDto>
}