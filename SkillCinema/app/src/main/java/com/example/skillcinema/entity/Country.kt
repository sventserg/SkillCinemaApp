package com.example.skillcinema.entity

interface Country: MovieFilter {
    override val name: String
    override val id: Int
}