package com.example.skillcinema.data.dto

import com.example.skillcinema.data.RESPONSE_PARAMETER_COUNTRY
import com.example.skillcinema.data.RESPONSE_PARAMETER_ID
import com.example.skillcinema.entity.Country
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CountryDto(
    @Json(name = RESPONSE_PARAMETER_ID) override val id: Int,
    @Json(name = RESPONSE_PARAMETER_COUNTRY) override val name: String
) : Country