package com.example.repository

import com.example.model.ScheduleList
import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import java.time.LocalDate

interface ScheduleRepository : CrudRepository<ScheduleList, Int> {
    @Query("select * from schedule")
    fun getAllInfo(): List<ScheduleList>?

    @Query("select * from schedule where id = :id")
    fun getScheduleById(id: Int): ScheduleList?

    @Query("select * from schedule where employee_id = :id")
    fun getAllByEmpId(id: Int): List<ScheduleList>?

    @Query("select * from schedule where responsibility_id = :respId")
    fun getAllByRespId(respId: Int): List<ScheduleList>?

    @Query("select * from schedule where :date between responsibility_start and responsibility_end")
    fun getAllByDate(date: LocalDate): List<ScheduleList>?

    @Query("select * from schedule where responsibility_id = :respId " +
            "and :date between responsibility_start and responsibility_end")
    fun getAllByDateAndResp(date: LocalDate, respId: Int): ScheduleList?

    @Query("select * from schedule order by id asc")
    fun ascendingSortById(): List<ScheduleList>?

    @Query("select * from schedule order by id desc")
    fun descendingSortById(): List<ScheduleList>?

    @Modifying
    @Query("update schedule set employee_id = :empId, responsibility_id = :respId," +
            "responsibility_start = :start, responsibility_end = :end where id = :scheduleId;")
    fun updateSchedule(scheduleId: Int, empId: Int, respId: Int, start: LocalDate, end: LocalDate)

    @Modifying
    @Query("truncate table schedule")
    fun clearTable()

    @Modifying
    @Query("delete from schedule where id = :scheduleId")
    fun deleteSchedule(scheduleId: Int)

    @Modifying
    @Query("delete from schedule where responsibility_end::date < :date::date")
    fun deleteOldSchedules(date: LocalDate)
}