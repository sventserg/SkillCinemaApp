package com.example.skillcinema.entity

interface PersonMovie {
    val filmId: Int
    val nameRu: String?
    val nameEn: String?
    val rating: String?
    val general: Boolean
    val description: String?
    val professionKey: String
    val posterUrl: String?
}