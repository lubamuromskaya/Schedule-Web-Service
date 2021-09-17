package com.example.service

import com.example.model.Schedule
import com.example.repository.ScheduleRepository
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class ScheduleService(val db: ScheduleRepository) {
    fun getScheduleById(id: Int): Schedule? = db.findByIdOrNull(id)

    @Transactional(readOnly = true)
    fun getAllByEmpId(id: Int): List<Schedule>? = db.getAllByEmployeeId(id)

    @Transactional(readOnly = true)
    fun getAllByRespId(respId: Int): List<Schedule>? = db.getAllByResponsibilityId(respId)

    @Transactional(readOnly = true)
    fun getAllByDate(date: LocalDate): List<Schedule>? = db.getAllByDate(date)

    @Transactional(readOnly = true)
    fun getAllByDateAndResp(date: LocalDate, respId: Int): Schedule? = db.getAllByDateAndResp(date, respId)

    @Transactional(readOnly = true)
    fun getByIdSortedAsc(): List<Schedule>? = db.findAll(Sort.by("id")).toList()

    @Transactional(readOnly = true)
    fun getByIdSortedDesc(): List<Schedule>? = db.findAll(Sort.by(Sort.Direction.DESC, "id")).toList()

    fun post(newSchedule: Schedule): Schedule = db.save(newSchedule)

    @Transactional
    fun update(id: Int, schedule: Schedule) = schedule.apply { this.id = id }.let(db::save)

    fun deleteSchedule(scheduleId: Int) = db.deleteById(scheduleId)

    @Transactional
    fun deleteOldSchedules(date: LocalDate) = db.deleteByEndDateLessThan(date)

    fun clearTable() = db.deleteAll()
}