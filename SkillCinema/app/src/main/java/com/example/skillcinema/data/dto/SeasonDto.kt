package com.example.skillcinema.data.dto

import com.example.skillcinema.data.RESPONSE_PARAMETER_EPISODES
import com.example.skillcinema.data.RESPONSE_PARAMETER_NUMBER
import com.example.skillcinema.entity.Season
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class SeasonDto(
    @Json(name = RESPONSE_PARAMETER_NUMBER) override val number: Int,
    @Json(name = RESPONSE_PARAMETER_EPISODES) override val episodes: List<EpisodeDto>
) : Season {
}