package com.example.service

import com.example.model.Responsibility
import com.example.repository.ResponsibilityRepository
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ResponsibilityService(val db: ResponsibilityRepository) {
    @Transactional(readOnly = true)
    fun getDaysNumberById(id: Int): Int = db.findById(id).get().daysNumber

    @Transactional(readOnly = true)
    fun getByIdSortedAsc(): List<Responsibility>? = db.findAll(Sort.by("id")).toList()

    @Transactional(readOnly = true)
    fun getByIdSortedDesc(): List<Responsibility>? = db.findAll(Sort.by(Sort.Direction.DESC, "id")).toList()

    @Transactional(readOnly = true)
    fun getByNameSortedAsc(): List<Responsibility>? = db.findAll(Sort.by("name")).toList()

    @Transactional(readOnly = true)
    fun getByNameSortedDesc(): List<Responsibility>? = db.findAll(Sort.by(Sort.Direction.DESC, "name")).toList()

    fun getRespById(id: Int): Responsibility? = db.findByIdOrNull(id)

    @Transactional
    fun update(id: Int, resp: Responsibility) = resp.apply { this.id = id }.let(db::save)

    fun post(newResp: Responsibility): Responsibility = db.save(newResp)

    fun delete(id: Int) = db.deleteById(id)

    fun clearTable() = db.deleteAll()
}
