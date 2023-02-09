package com.example.skillcinema.entity

interface Staff {
    val staffId: Int
    val nameRu: String?
    val nameEn: String?
    val description: String?
    val posterUrl: String
    val professionText: String
    val professionKey: String

    fun profession(): StaffProfessionKey
}