package com.example.skillcinema.entity

interface Person {
    val personId: Int
    val webUrl: String?
    val nameRu: String?
    val nameEn: String?
    val sex: String?
    val posterUrl: String
    val growth: String?
    val birthday: String?
    val death: String?
    val age: Int?
    val birthPlace: String?
    val deathPlace: String?
    val hasAwards: Int?
    val profession: String?
    val facts: List<String>?
    val films: List<PersonMovie>

    fun name() : String
}