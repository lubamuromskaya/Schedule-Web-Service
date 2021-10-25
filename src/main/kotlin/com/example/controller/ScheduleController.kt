package com.example.controller

import com.example.model.Schedule
import com.example.service.EmployeeService
import com.example.service.ResponsibilityService
import com.example.service.ScheduleService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDate

@RestController
@RequestMapping("/schedule")
class ScheduleController(val scheduleService: ScheduleService,
                         val respService: ResponsibilityService,
                         val empService: EmployeeService
) {
    @GetMapping
    fun getAllInfo(@RequestParam("ordering") orderType: String?): List<Schedule>? =
        when (orderType) {
            "id" -> scheduleService.getByIdSortedAsc()
            "-id" -> scheduleService.getByIdSortedDesc()
            else -> scheduleService.getByIdSortedAsc()
        }

    @GetMapping("/{id}")
    fun getScheduleById(@PathVariable("id") id: Int): Schedule? {
        return scheduleService.getScheduleById(id)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule with this id does not exist.")
    }

    @GetMapping("/emp/{id}")
    fun getByEmp(@PathVariable("id") id: Int): List<Schedule>? = scheduleService.getAllByEmpId(id)

    @GetMapping("/resp/{id}")
    fun getByResp(@PathVariable("id") id: Int): List<Schedule>? = scheduleService.getAllByRespId(id)

    @GetMapping("/date/{date}")
    fun getByDate(@PathVariable("date") date: LocalDate): List<Schedule>? = scheduleService.getAllByDate(date)

    @GetMapping("/date/{date}/resp/{resp}")
    fun getByDateAndResp(@PathVariable("date") date: LocalDate,
                         @PathVariable("resp") respId: Int): Schedule? = scheduleService.getAllByDateAndResp(date, respId)

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    fun postSchedule(@RequestBody newSchedule: Schedule): Schedule? {
        if (respService.getRespById(newSchedule.responsibilityId) == null)
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Responsibility with this id does not exist in Responsibility table.")
        if (empService.getEmpById(newSchedule.employeeId) == null)
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Employee with this id does not exist in Employee table.")
        else {
            val daysNumber: Int = respService.getDaysNumberById(newSchedule.responsibilityId)
            newSchedule.endDate = newSchedule.startDate.plusDays(daysNumber.toLong() - 1)
            return scheduleService.post(newSchedule)
        }
    }

    @PatchMapping("/{id}")
    fun updateSchedule(@PathVariable("id") id: Int, @RequestBody schedule: Schedule): Schedule {
        if (respService.getRespById(schedule.responsibilityId) == null)
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Responsibility with this id does not exist in Responsibility table.")
        if (empService.getEmpById(schedule.employeeId) == null)
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Employee with this id does not exist in Employee table.")
        if (scheduleService.getScheduleById(id) == null)
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule with this id does not exist in Schedule table.")
        else {
            val daysNumber: Int = respService.getDaysNumberById(schedule.responsibilityId)
            schedule.endDate = schedule.startDate.plusDays(daysNumber.toLong())

            return scheduleService.update(id, schedule)
        }
    }

    @DeleteMapping("/clear")
    fun clearTable() {
        scheduleService.clearTable()
    }

    @DeleteMapping("/{id}")
    fun deleteSchedule(@PathVariable("id") id: Int) {
        if (scheduleService.getScheduleById(id) == null)
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "This schedule does not exist in this table.")
        else
            scheduleService.deleteSchedule(id)
    }

    @DeleteMapping("/deleteOld")
    fun deleteOldSchedules() {
        val currentDate: LocalDate = LocalDate.now()
        scheduleService.deleteOldSchedules(currentDate)
    }
}