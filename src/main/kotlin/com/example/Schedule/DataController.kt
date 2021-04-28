package com.example.Schedule

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import java.time.LocalDate

// CrudRepository: обеспечивает основные операции по поиску, сохранения, удалению данных (CRUD операции)
interface ScheduleRepository : CrudRepository<ScheduleList, Int> {
    // Call of method findMessages() will execute the corresponding database query.
    // This query retrieves a list of all Message objects in the database table.
    @Query("select * from schedule")
    fun getAllInfo(): List<ScheduleList>

    @Query("select * from schedule where schedule.employee_id = :id")
    fun getEmpById(id: Int): ScheduleList?

    @Query("select * from schedule where :date between responsibility_start and responsibility_end")
    fun getEmpByDate(date: LocalDate): ScheduleList?

    @Query("select * from schedule where responsibility_id = :respId")
    fun getEmpByResp(respId: Int): ScheduleList?

    @Query("select * from schedule where responsibility_id = :respId " +
            "and :date between responsibility_start and responsibility_end")
    fun getEmpByDateAndResp(date: LocalDate, respId: Int): ScheduleList?
}