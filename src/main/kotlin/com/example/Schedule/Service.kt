package com.example.Schedule

import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ScheduleService(val db: ScheduleRepository) {
    // get full Schedule table with all information
    fun getAllInfo(): List<ScheduleList>? = db.getAllInfo()

    fun getByScheduleId(id: Int): ScheduleList? = db.getByScheduleId(id)

    fun getAllByEmpId(id: Int): List<ScheduleList>? = db.getAllByEmpId(id)

    // get employee by responsibility id
    fun getEmpByResp(respId: Int): List<ScheduleList>? = db.getEmpByResp(respId)

    // get employees with their responsibilities by date
    fun getEmpByDate(date: LocalDate): List<ScheduleList>? = db.getEmpByDate(date)

    // get employee by date and responsibility id
    fun getEmpByDateAndResp(date: LocalDate, respId: Int): ScheduleList? = db.getEmpByDateAndResp(date, respId)

    // delete all information from table
    fun clearTable() = db.clearTable()

    // post new employee with responsibility, date of starting and date of ending in Schedule
    fun postSchedule(newSchedule: ScheduleList): ScheduleList = db.save(newSchedule) // using default save method

    // delete employee with his responsibility from Schedule
    fun deleteSchedule(scheduleId: Int) = db.deleteSchedule(scheduleId)

    fun deleteOldSchedules(date: LocalDate) = db.deleteOldSchedules(date)

    fun updateSchedule(scheduleId: Int, empId: Int, respId: Int, start: LocalDate, end: LocalDate) = db.updateSchedule(scheduleId, empId, respId, start, end)

}

@Service
class ResponsibilitiesService(val db: ResponsibilitiesRepository) {
    // get list of responsibilities
    fun getRespList(): List<Responsibility>? = db.getRespList()

    fun ascSortById(): List<Responsibility>? = db.ascendingSortById()

    fun descSortById(): List<Responsibility>? = db.descendingSortById()

    fun ascSortByName(): List<Responsibility>? = db.ascendingSortByName()

    fun descSortByName(): List<Responsibility>? = db.descendingSortByName()

    fun getDaysNumberById(id: Int): Long = db.getDaysNumberById(id)

    fun getRespById(id: Int): Responsibility? = db.getRespById(id)

    fun isExists(respName: String, daysNumber: Int): Responsibility? = db.isExists(respName, daysNumber)

    fun updateResp(id: Int, name: String, days: Int) = db.updateResp(id, name, days)

    fun clearTable() = db.clearTable()

    fun postResp(newResp: Responsibility): Responsibility = db.save(newResp) // using default save method

    fun deleteResp(id: Int) = db.deleteById(id) // using default delete method
}

@Service
class EmployeeService(val db: EmployeeRepository) {
    fun getEmpList(): List<Employee>? = db.getEmpList()

    fun ascSortById(): List<Employee>? = db.ascendingSortById()

    fun descSortById(): List<Employee>? = db.descendingSortById()

    fun ascSortByName(): List<Employee>? = db.ascendingSortByName()

    fun descSortByName(): List<Employee>? = db.descendingSortByName()

    fun getEmpById(id: Int): Employee? = db.getEmpById(id)

    fun updateEmp(id: Int, name: String) = db.updateEmp(id, name)

    fun clearTable() = db.clearTable()

    fun postEmp(emp: Employee) = db.save(emp) // using default save method

    fun deleteEmp(id: Int) = db.deleteById(id) // using default delete method

}