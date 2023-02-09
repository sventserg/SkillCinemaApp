package com.example.skillcinema.data.dto

import com.example.skillcinema.data.*
import com.example.skillcinema.entity.Episode
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class EpisodeDto(
    @Json(name = RESPONSE_PARAMETER_SEASON_NUMBER) override val seasonNumber: Int,
    @Json(name = RESPONSE_PARAMETER_EPISODE_NUMBER) override val episodeNumber: Int,
    @Json(name = RESPONSE_PARAMETER_NAME_RU) override val nameRu: String?,
    @Json(name = RESPONSE_PARAMETER_NAME_EN) override val nameEn: String?,
    @Json(name = RESPONSE_PARAMETER_SYNOPSIS) override val synopsis: String?,
    @Json(name = RESPONSE_PARAMETER_RELEASE_DATE) override val releaseDate: String?
) : Episode {
}