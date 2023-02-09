package com.example.skillcinema.domain

import com.example.skillcinema.entity.Staff

class GetFilteredStaff {
    fun getActors(staff: List<Staff>): List<Staff> {
        val actors = mutableListOf<Staff>()
        staff.forEach {
            if (it.professionKey == "ACTOR") {
                actors.add(it)
            }
        }
        return actors
    }

    fun getWorkers(staff: List<Staff>): List<Staff> {
        val workers = mutableListOf<Staff>()
        staff.forEach {
            if (it.professionKey != "ACTOR") {
                workers.add(it)
            }
        }
        return workers
    }
}