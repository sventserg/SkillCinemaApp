package com.example.skillcinema.data.dto

import com.example.skillcinema.data.RESPONSE_PARAMETER_ITEMS
import com.example.skillcinema.data.RESPONSE_PARAMETER_TOTAL
import com.example.skillcinema.entity.Seasons
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class SeasonsDto(
    @Json(name = RESPONSE_PARAMETER_TOTAL) override val total: Int,
    @Json(name = RESPONSE_PARAMETER_ITEMS) override val items: List<SeasonDto>
) : Seasons {
}