package com.example.skillcinema.entity

interface Genre : MovieFilter {
    override val name: String
    override val id: Int
}