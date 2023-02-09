package com.example.skillcinema.data.api

import com.example.skillcinema.data.GET_SEASONS_PATH
import com.example.skillcinema.data.KINOPOISK_API_KEY
import com.example.skillcinema.data.dto.SeasonsDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface LoadSeasonsApi {
    @Headers(KINOPOISK_API_KEY)
    @GET(GET_SEASONS_PATH)
    fun loadSeasons(
        @Path("id") id: Int
    ): Call<SeasonsDto>
}