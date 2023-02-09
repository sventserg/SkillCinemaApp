package com.example.skillcinema.entity

interface SavedMovie {
    val id: Int
    val name: String
    val genre: String
    val poster: String
    val rating: String
    var isViewed: Boolean
}
