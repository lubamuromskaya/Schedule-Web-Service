package com.example.service

import com.example.model.Responsibility
import com.example.repository.ResponsibilityRepository
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ResponsibilityService(val db: ResponsibilityRepository) {
    fun getDaysNumberById(id: Int): Int = db.findById(id).get().daysNumber

    fun getRespById(id: Int): Responsibility? = db.findByIdOrNull(id)

    fun ascSortById(): List<Responsibility>? = db.findAll(Sort.by("id")).toList()

    fun descSortById(): List<Responsibility>? = db.findAll(Sort.by(Sort.Direction.DESC, "id")).toList()

    fun ascSortByName(): List<Responsibility>? = db.findAll(Sort.by("name")).toList()

    fun descSortByName(): List<Responsibility>? = db.findAll(Sort.by(Sort.Direction.DESC, "name")).toList()

    fun updateResp(id: Int, resp: Responsibility) = resp.apply { this.id = id }.let(db::save)

    fun clearTable() = db.deleteAll()

    fun postResp(newResp: Responsibility): Responsibility = db.save(newResp)

    fun deleteResp(id: Int) = db.deleteById(id)
}
