package com.example.skillcinema.entity.data.repository

import com.example.skillcinema.entity.Person

interface LoadPersonRepository {
    suspend fun loadPerson(personId: Int): Person?
}