package com.example.Schedule

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import java.time.LocalDate

// CrudRepository: обеспечивает основные операции по поиску, сохранения, удалению данных (CRUD операции)
interface ScheduleRepository : CrudRepository<ScheduleList, Int> {
    @Query("select * from schedule")
    fun getAllInfo(): List<ScheduleList>?

    @Query("select * from schedule where :date between responsibility_start and responsibility_end")
    fun getEmpByDate(date: LocalDate): ScheduleList?

    @Query("select * from schedule where responsibility_id = :respId")
    fun getEmpByResp(respId: Int): ScheduleList?

    @Query("select * from schedule where responsibility_id = :respId " +
            "and :date between responsibility_start and responsibility_end")
    fun getEmpByDateAndResp(date: LocalDate, respId: Int): ScheduleList?

    @Query("delete from schedule where employee_id = :empId and responsibility_id = :respId")
    fun delete(empId: Int, respId: Int)

    fun updateEmp(respId: Int, oldEmpId: Int, newEmpId: Int)

    fun updateResp(empId: Int, oldRespId: Int, newRespId:Int)
}


// interface for communicate with Responsibilities table
interface ResponsibilitiesRepository: CrudRepository<Responsibility, Int> {
    @Query("select * from responsibilities")
    fun getRespList(): List<Responsibility>?

    @Query("select responsibility_name from responsibilities where responsibility_id = :id")
    fun getNameById(id: Int): Responsibility?

    @Query("select responsibility_id from responsibilities where responsibility_name = :name")
    fun getIdByName(name: String): Responsibility?
}


interface EmployeeRepository: CrudRepository<Employee, Int> {
    @Query("select * from employee")
    fun getEmpList(): List<Employee>?

    @Query("select * from employee where employee_id = :id")
    fun getEmpById(id: Int): Employee?

    @Query("delete from employee where employee_id = :id")
    fun deleteEmp(id: Int)


}