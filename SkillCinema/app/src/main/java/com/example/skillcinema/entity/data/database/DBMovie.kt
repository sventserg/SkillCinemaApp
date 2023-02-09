package com.example.skillcinema.entity.data.database

interface DBMovie {
    val id: Int
    val name: String
    val genre: String
    val posterUrl: String
    val rating: String?
}