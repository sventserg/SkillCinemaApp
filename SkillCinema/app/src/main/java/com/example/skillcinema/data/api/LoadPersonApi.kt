package com.example.skillcinema.data.api

import com.example.skillcinema.data.GET_PERSON_PATH
import com.example.skillcinema.data.KINOPOISK_API_KEY
import com.example.skillcinema.data.dto.PersonDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface LoadPersonApi {
    @Headers(KINOPOISK_API_KEY)
    @GET(GET_PERSON_PATH)
    fun loadPersonApi(
        @Path("id") id: Int
    ): Call<PersonDto>
}