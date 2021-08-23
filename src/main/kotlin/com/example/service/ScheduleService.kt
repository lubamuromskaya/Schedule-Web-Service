package com.example.service

import com.example.model.ScheduleList
import com.example.repository.ScheduleRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ScheduleService(val db: ScheduleRepository) {
    fun getAllInfo(): List<ScheduleList>? = db.getAllInfo()

    fun getScheduleById(id: Int): ScheduleList? = db.getScheduleById(id)

    fun getAllByEmpId(id: Int): List<ScheduleList>? = db.getAllByEmpId(id)

    fun getAllByRespId(respId: Int): List<ScheduleList>? = db.getAllByRespId(respId)

    fun getAllByDate(date: LocalDate): List<ScheduleList>? = db.getAllByDate(date)

    fun getAllByDateAndResp(date: LocalDate, respId: Int): ScheduleList? = db.getAllByDateAndResp(date, respId)

    fun ascSortById(): List<ScheduleList>? = db.ascendingSortById()

    fun descSortById(): List<ScheduleList>? = db.descendingSortById()

    fun postSchedule(newSchedule: ScheduleList): ScheduleList = db.save(newSchedule)

    fun updateSchedule(scheduleId: Int, empId: Int, respId: Int, start: LocalDate, end: LocalDate)
            = db.updateSchedule(scheduleId, empId, respId, start, end)

    fun clearTable() = db.clearTable()

    fun deleteSchedule(scheduleId: Int) = db.deleteSchedule(scheduleId)

    fun deleteOldSchedules(date: LocalDate) = db.deleteOldSchedules(date)
}