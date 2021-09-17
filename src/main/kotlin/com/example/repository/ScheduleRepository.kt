package com.example.repository

import com.example.model.Schedule
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import java.time.LocalDate

interface ScheduleRepository : PagingAndSortingRepository<Schedule, Int> {
    fun getAllByEmployeeId(id: Int): List<Schedule>

    fun getAllByResponsibilityId(respId: Int): List<Schedule>

    @Query("select s from Schedule s where :date between start_date and end_date")
    fun getAllByDate(date: LocalDate): List<Schedule>

    @Query(
        "select s from Schedule s where responsibility_id = :respId " +
                "and :date between start_date and end_date"
    )
    fun getAllByDateAndResp(date: LocalDate, respId: Int): Schedule?

    fun deleteByEndDateLessThan(date: LocalDate)
}