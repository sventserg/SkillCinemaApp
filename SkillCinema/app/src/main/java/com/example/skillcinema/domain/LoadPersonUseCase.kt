package com.example.skillcinema.domain

import com.example.skillcinema.entity.Person
import com.example.skillcinema.entity.repository.LoadPersonRepository

class LoadPersonUseCase(
    private val repository: LoadPersonRepository
) {
    suspend fun loadPerson(personId: Int): Person? {
        return repository.loadPerson(personId)
    }
}