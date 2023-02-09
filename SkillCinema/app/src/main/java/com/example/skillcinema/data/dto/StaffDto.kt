package com.example.skillcinema.data.dto

import com.example.skillcinema.data.*
import com.example.skillcinema.entity.Staff
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class StaffDto(
    @Json(name = RESPONSE_PARAMETER_STAFF_ID) override val staffId: Int,
    @Json(name = RESPONSE_PARAMETER_NAME_RU) override val nameRu: String?,
    @Json(name = RESPONSE_PARAMETER_NAME_EN) override val nameEn: String?,
    @Json(name = RESPONSE_PARAMETER_DESCRIPTION) override val description: String?,
    @Json(name = RESPONSE_PARAMETER_POSTER_URL) override val posterUrl: String,
    @Json(name = RESPONSE_PARAMETER_PROFESSION_TEXT) override val professionText: String,
    @Json(name = RESPONSE_PARAMETER_PROFESSION_KEY) override val professionKey: String
) : Staff {
    override fun profession(): StaffProfessionKeyDto {
        return when (professionKey) {
            PROFESSION_KEY_WRITER -> StaffProfessionKeyDto.WRITER
            PROFESSION_KEY_OPERATOR -> StaffProfessionKeyDto.OPERATOR
            PROFESSION_KEY_EDITOR -> StaffProfessionKeyDto.EDITOR
            PROFESSION_KEY_COMPOSER -> StaffProfessionKeyDto.COMPOSER
            PROFESSION_KEY_PRODUCER_USSR -> StaffProfessionKeyDto.PRODUCER_USSR
            PROFESSION_KEY_TRANSLATOR -> StaffProfessionKeyDto.TRANSLATOR
            PROFESSION_KEY_DIRECTOR -> StaffProfessionKeyDto.DIRECTOR
            PROFESSION_KEY_DESIGN -> StaffProfessionKeyDto.DESIGN
            PROFESSION_KEY_PRODUCER -> StaffProfessionKeyDto.PRODUCER
            PROFESSION_KEY_ACTOR -> StaffProfessionKeyDto.ACTOR
            PROFESSION_KEY_VOICE_DIRECTOR -> StaffProfessionKeyDto.VOICE_DIRECTOR
            else -> StaffProfessionKeyDto.UNKNOWN
        }
    }
}