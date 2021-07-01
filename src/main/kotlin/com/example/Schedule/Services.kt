package com.example.Schedule

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

@Service
class ResponsibilitiesService(val db: ResponsibilitiesRepository) {
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

@Service
class EmployeeService(val db: EmployeeRepository) {
    fun getEmpList(): List<Employee>? = db.getEmpList()

    fun getEmpById(id: Int): Employee? = db.getEmpById(id)

    fun ascSortById(): List<Employee>? = db.ascendingSortById()

    fun descSortById(): List<Employee>? = db.descendingSortById()

    fun ascSortByName(): List<Employee>? = db.ascendingSortByName()

    fun descSortByName(): List<Employee>? = db.descendingSortByName()

    fun updateEmp(id: Int, name: String) = db.updateEmp(id, name)

    fun clearTable() = db.clearTable()

    fun postEmp(emp: Employee): Employee = db.save(emp)

    fun deleteEmp(id: Int) = db.deleteById(id)

}