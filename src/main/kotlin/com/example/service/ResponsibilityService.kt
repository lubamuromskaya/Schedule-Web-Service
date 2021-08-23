package com.example.service

import com.example.model.Responsibility
import com.example.repository.ResponsibilityRepository
import org.springframework.stereotype.Service

@Service
class ResponsibilityService(val db: ResponsibilityRepository) {
    fun getRespList(): List<Responsibility>? = db.getRespList()

    fun getDaysNumberById(id: Int): Long = db.getDaysNumberById(id)

    fun getRespById(id: Int): Responsibility? = db.getRespById(id)

    fun isExists(respName: String, daysNumber: Int): Responsibility? = db.isExists(respName, daysNumber)

    fun ascSortById(): List<Responsibility>? = db.ascendingSortById()

    fun descSortById(): List<Responsibility>? = db.descendingSortById()

    fun ascSortByName(): List<Responsibility>? = db.ascendingSortByName()

    fun descSortByName(): List<Responsibility>? = db.descendingSortByName()

    fun updateResp(id: Int, name: String, days: Int) = db.updateResp(id, name, days)

    fun clearTable() = db.clearTable()

    fun postResp(newResp: Responsibility): Responsibility = db.save(newResp)

    fun deleteResp(id: Int) = db.deleteById(id)
}
