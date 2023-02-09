package com.example.skillcinema.data.dto

import com.example.skillcinema.data.*
import com.example.skillcinema.entity.PersonMovie
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class PersonMovieDto(
    @Json(name = RESPONSE_PARAMETER_FILM_ID) override val filmId: Int,
    @Json(name = RESPONSE_PARAMETER_NAME_RU) override val nameRu: String?,
    @Json(name = RESPONSE_PARAMETER_NAME_EN) override val nameEn: String?,
    @Json(name = RESPONSE_PARAMETER_RATING) override val rating: String?,
    @Json(name = RESPONSE_PARAMETER_GENERAL) override val general: Boolean,
    @Json(name = RESPONSE_PARAMETER_DESCRIPTION) override val description: String?,
    @Json(name = RESPONSE_PARAMETER_PROFESSION_KEY) override val professionKey: String,
    @Json(name = RESPONSE_PARAMETER_POSTER_URL) override val posterUrl: String?
) : PersonMovie {
}