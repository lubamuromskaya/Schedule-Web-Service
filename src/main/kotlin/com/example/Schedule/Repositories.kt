package com.example.Schedule

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


interface ResponsibilitiesRepository: CrudRepository<Responsibility, Int> {
    @Query("select * from responsibilities")
    fun getRespList(): List<Responsibility>?

    @Query("select days_number from responsibilities where responsibility_id = :id")
    fun getDaysNumberById(id: Int): Long

    @Query("select * from responsibilities where responsibility_id = :id")
    fun getRespById(id: Int): Responsibility?

    @Query("select * from responsibilities where responsibility_name = :respName and days_number = :daysNumber")
    fun isExists(respName: String, daysNumber: Int): Responsibility?

    @Query("select * from responsibilities order by responsibility_id asc")
    fun ascendingSortById(): List<Responsibility>?

    @Query("select * from responsibilities order by responsibility_id desc")
    fun descendingSortById(): List<Responsibility>?

    @Query("select * from responsibilities order by responsibility_name asc")
    fun ascendingSortByName(): List<Responsibility>?

    @Query("select * from responsibilities order by responsibility_name desc")
    fun descendingSortByName(): List<Responsibility>?

    @Modifying
    @Query("update responsibilities set responsibility_name = :name, " +
            "days_number = :days where responsibility_id = :id;")
    fun updateResp(id: Int, name: String, days: Int)

    @Modifying
    @Query("truncate table responsibilities")
    fun clearTable()
}


interface EmployeeRepository: CrudRepository<Employee, Int> {
    @Query("select * from employee")
    fun getEmpList(): List<Employee>?

    @Query("select * from employee where employee_id = :id;")
    fun getEmpById(id: Int): Employee?

    @Query("select * from employee order by employee_id asc")
    fun ascendingSortById(): List<Employee>?

    @Query("select * from employee order by employee_id desc")
    fun descendingSortById(): List<Employee>?

    @Query("select * from employee order by employee_name asc")
    fun ascendingSortByName(): List<Employee>?

    @Query("select * from employee order by employee_name desc")
    fun descendingSortByName(): List<Employee>?

    @Modifying
    @Query("update employee set employee_name = :name where employee_id = :id;")
    fun updateEmp(id: Int, name: String)

    @Modifying
    @Query("truncate table employee")
    fun clearTable()

}