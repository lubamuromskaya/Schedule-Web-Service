package com.example.Schedule

import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ScheduleService(val db: ScheduleRepository) {
    // get full Schedule table with all information
    fun getAll(): List<ScheduleList>? = db.getAllInfo()

    // get employee by date
    fun getEmpByDate(date: LocalDate): ScheduleList? = db.getEmpByDate(date)

    // get employee by responsibility id
    fun getEmpByResp(respId: Int): ScheduleList? = db.getEmpByResp(respId)

    // get employee by date and responsibility id
    fun getEmpByDateAndResp(date: LocalDate, respId: Int): ScheduleList? = db.getEmpByDateAndResp(date, respId)

    fun delete(empId: Int, respId: Int) = db.delete(empId, respId)

    // post new employee with responsibility, date of starting and date of ending into table Schedule
    fun post(newSchedule: ScheduleList) = db.save(newSchedule) // using default save method

}

@Service
class ResponsibilitiesService(val db: ResponsibilitiesRepository) {
    fun getRespList(): List<Responsibility>? = db.getRespList()

    fun getNameById(id: Int): Responsibility? = db.getNameById(id)

    fun getIdByName(name: String): Responsibility? = db.getIdByName(name)

    fun deleteResp(id: Int) = db.deleteById(id) // using default delete method

    fun post(newResp: Responsibility) = db.save(newResp) // using default save method

}

@Service
class EmployeeService(val db: EmployeeRepository) {
    fun getEmpList(): List<Employee>? = db.getEmpList()

    fun getEmpById(id: Int): Employee? = db.getEmpById(id)

    fun deleteEmp(id: Int) = db.deleteById(id) // using default delete method

    fun post(newEmp: Employee) = db.save(newEmp) // using default save method
}