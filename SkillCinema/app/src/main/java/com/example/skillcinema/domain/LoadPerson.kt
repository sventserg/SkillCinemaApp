package com.example.skillcinema.domain

import com.example.skillcinema.entity.Person
import com.example.skillcinema.entity.data.repository.LoadPersonRepository

class LoadPerson(
    private val repository: LoadPersonRepository
) {
    suspend fun loadPerson(personId: Int): Person? {
        return repository.loadPerson(personId)
    }
}