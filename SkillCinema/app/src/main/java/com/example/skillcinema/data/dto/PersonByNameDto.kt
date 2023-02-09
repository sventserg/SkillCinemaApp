package com.example.skillcinema.data.dto

import com.example.skillcinema.data.*
import com.example.skillcinema.entity.PersonByName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class PersonByNameDto(
    @Json(name = RESPONSE_PARAMETER_KINOPOISK_ID) override val kinopoiskID: Int,
    @Json(name = RESPONSE_PARAMETER_WEB_URL) override val webUrl: String,
    @Json(name = RESPONSE_PARAMETER_NAME_RU) override val nameRu: String?,
    @Json(name = RESPONSE_PARAMETER_NAME_EN) override val nameEn: String?,
    @Json(name = RESPONSE_PARAMETER_POSTER_URL) override val posterUrl: String
) : PersonByName {
}