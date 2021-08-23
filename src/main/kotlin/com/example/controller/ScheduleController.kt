package com.example.controller

import com.example.model.ScheduleList
import com.example.service.EmployeeService
import com.example.service.ResponsibilityService
import com.example.service.ScheduleService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDate

@RestController
class ScheduleController(val scheduleService: ScheduleService,
                         val respService: ResponsibilityService,
                         val empService: EmployeeService
) {
    @GetMapping("/schedule")
    fun getAllInfo(@RequestParam("ordering") orderType: String?): List<ScheduleList>? =
        when (orderType) {
            "id" -> scheduleService.ascSortById()
            "-id" -> scheduleService.descSortById()
            else -> scheduleService.getAllInfo()
        }

    @GetMapping("/schedule/getById")
    fun getScheduleById(@RequestParam("id") id: Int): ScheduleList? {
        return scheduleService.getScheduleById(id)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule with this id does not exist.")
    }

    @GetMapping("/schedule/getEmp")
    fun getByEmp(@RequestParam("id") id: Int): List<ScheduleList>? {
        val answer = scheduleService.getAllByEmpId(id)
        if (answer.isNullOrEmpty())
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "This employee does not exist in this table.")
        else
            return answer
    }

    @GetMapping("/schedule/getResp")
    fun getByResp(@RequestParam("id") id: Int): List<ScheduleList>? {
        val answer: List<ScheduleList>? = scheduleService.getAllByRespId(id)
        if (answer.isNullOrEmpty())
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "This responsibility does not exist in this table.")
        else
            return answer
    }

    @GetMapping("/schedule/getByDate")
    fun getByDate(@RequestParam("date") date: LocalDate): List<ScheduleList>? {
        val answer: List<ScheduleList>? = scheduleService.getAllByDate(date)
        if (answer.isNullOrEmpty())
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "This date does not exist in this table.")
        else
            return answer
    }

    @GetMapping("/schedule/getBy")
    fun getByDateAndResp(@RequestHeader("Date") date: LocalDate,
                         @RequestHeader("Resp") respId: Int): ScheduleList? {
        return scheduleService.getAllByDateAndResp(date, respId)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "This date or responsibility does not exist in this table.")
    }

    @PostMapping("/schedule")
    @ResponseBody
    fun postSchedule(@RequestBody newSchedule: ScheduleList): ScheduleList? {
        if (respService.getRespById(newSchedule.responsibilityId) == null)
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Responsibility with this id does not exist in Responsibilities table.")
        if (empService.getEmpById(newSchedule.employeeId) == null)
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Employee with this id does not exist in Employee table.")
        else {
            val daysNumber: Long = respService.getDaysNumberById(newSchedule.responsibilityId)
            newSchedule.responsibilityEnd = newSchedule.responsibilityStart.plusDays(daysNumber)
            return scheduleService.postSchedule(newSchedule)
        }
    }

    @PatchMapping("/schedule")
    @ResponseBody
    fun updateSchedule(@RequestBody newSchedule: ScheduleList) {
        if (respService.getRespById(newSchedule.responsibilityId) == null)
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Responsibility with this id does not exist in Responsibilities table.")
        if (empService.getEmpById(newSchedule.employeeId) == null)
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Employee with this id does not exist in Employee table.")
        if (scheduleService.getScheduleById(newSchedule.id) == null)
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule with this id does not exist in Schedule table.")
        else {
            val daysNumber: Long = respService.getDaysNumberById(newSchedule.responsibilityId)
            newSchedule.responsibilityEnd = newSchedule.responsibilityStart.plusDays(daysNumber)

            return scheduleService.updateSchedule(newSchedule.id, newSchedule.employeeId,
                newSchedule.responsibilityId,
                newSchedule.responsibilityStart,
                newSchedule.responsibilityEnd)
        }
    }

    @PatchMapping("/schedule/clear")
    fun clearTable() {
        scheduleService.clearTable()
    }

    @DeleteMapping("/schedule")
    fun deleteSchedule(@RequestParam("id") id: Int) {
        if (scheduleService.getScheduleById(id) == null)
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "This date does not exist in this table.")
        else
            scheduleService.deleteSchedule(id)
    }

    @DeleteMapping("/schedule/deleteOld")
    fun deleteOldSchedules() {
        val currentDate: LocalDate = LocalDate.now()
        scheduleService.deleteOldSchedules(currentDate)
    }
}