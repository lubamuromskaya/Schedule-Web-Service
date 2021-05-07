package com.example.Schedule

import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ScheduleService(val db: ScheduleRepository) {
    // get full Schedule table with all information
    fun getAll(): List<ScheduleList>? = db.getAllInfo()

    // get employees with their responsibilities by date
    fun getEmpByDate(date: LocalDate): ScheduleList? = db.getEmpByDate(date)

    // get employee by responsibility id
    fun getEmpByResp(respId: Int): ScheduleList? = db.getEmpByResp(respId)

    // get employee by date and responsibility id
    fun getEmpByDateAndResp(date: LocalDate, respId: Int): ScheduleList? = db.getEmpByDateAndResp(date, respId)

    // delete employee with his responsibility from Schedule
    fun delete(empId: Int, respId: Int) = db.delete(empId, respId)

    // post new employee with responsibility, date of starting and date of ending in Schedule
    fun post(newSchedule: ScheduleList) = db.save(newSchedule) // using default save method

}

@Service
class ResponsibilitiesService(val db: ResponsibilitiesRepository) {
    // get list of responsibilities
    fun getRespList(): List<Responsibility>? = db.getRespList()

    fun ascSortById(): List<Responsibility>? = db.ascendingSortById()

    fun descSortById(): List<Responsibility>? = db.descendingSortById()

    // get responsibility by id
    fun getRespById(id: Int): Responsibility? = db.getRespById(id)

    // post new responsibility
    fun postNewResp(newResp: Responsibility): Responsibility = db.save(newResp) // using default save method

    // delete responsibility by id
    fun deleteResp(id: Int) = db.deleteById(id) // using default delete method

    fun updateResp(id: Int, name: String) = db.updateResp(id, name)

}

@Service
class EmployeeService(val db: EmployeeRepository) {
    fun getEmpList(): List<Employee>? = db.getEmpList()

    fun getEmpById(id: Int): Employee? = db.getEmpById(id)

    fun postNewEmp(emp: Employee) = db.save(emp)

    fun deleteEmp(id: Int) = db.deleteById(id) // using default delete method

    fun updateEmp(id: Int, name: String) = db.updateEmp(id, name)

    fun ascSortById(): List<Employee>? = db.ascendingSortById()

    fun descSortById(): List<Employee>? = db.descendingSortById()
}