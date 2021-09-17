package com.example.service

import com.example.model.Schedule
import com.example.repository.ScheduleRepository
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ScheduleService(val db: ScheduleRepository) {
    fun getScheduleById(id: Int): Schedule? = db.findByIdOrNull(id)

    fun getAllByEmpId(id: Int): List<Schedule>? = db.getAllByEmployeeId(id)

    fun getAllByRespId(respId: Int): List<Schedule>? = db.getAllByResponsibilityId(respId)

    fun getAllByDate(date: LocalDate): List<Schedule>? = db.getAllByDate(date)

    fun getAllByDateAndResp(date: LocalDate, respId: Int): Schedule? = db.getAllByDateAndResp(date, respId)

    fun ascSortById(): List<Schedule>? = db.findAll(Sort.by("id")).toList()

    fun descSortById(): List<Schedule>? = db.findAll(Sort.by(Sort.Direction.DESC, "id")).toList()

    fun postSchedule(newSchedule: Schedule): Schedule = db.save(newSchedule)

    fun updateSchedule(id: Int, schedule: Schedule) = schedule.apply { this.id = id }.let(db::save)

    fun clearTable() = db.deleteAll()

    fun deleteSchedule(scheduleId: Int) = db.deleteById(scheduleId)

    fun deleteOldSchedules(date: LocalDate) = db.deleteByEndDateLessThan(date)
}