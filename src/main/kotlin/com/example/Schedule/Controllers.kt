package com.example.Schedule

import org.springframework.data.relational.core.conversion.DbActionExecutionException
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.jdbc.UncategorizedSQLException
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDate

@RestController
class ScheduleController(val scheduleService: ScheduleService,
                         val respService: ResponsibilitiesService,
                         val empService: EmployeeService) {
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
            ?: throw ResponseStatusException(NOT_FOUND, "Schedule with this id does not exist.")
    }


    @GetMapping("/schedule/getEmp")
    fun getByEmp(@RequestParam("id") id: Int): List<ScheduleList>? {
        val answer = scheduleService.getAllByEmpId(id)
        if (answer.isNullOrEmpty())
            throw ResponseStatusException(NOT_FOUND, "This employee does not exist in this table.")
        else
            return answer
    }


    @GetMapping("/schedule/getResp")
    fun getByResp(@RequestParam("id") id: Int): List<ScheduleList>? {
        val answer: List<ScheduleList>? = scheduleService.getAllByRespId(id)
        if (answer.isNullOrEmpty())
            throw ResponseStatusException(NOT_FOUND, "This responsibility does not exist in this table.")
        else
            return answer
    }


    @GetMapping("/schedule/getByDate")
    fun getByDate(@RequestParam("date") date: LocalDate): List<ScheduleList>? {
        val answer: List<ScheduleList>? = scheduleService.getAllByDate(date)
        if (answer.isNullOrEmpty())
            throw ResponseStatusException(NOT_FOUND, "This date does not exist in this table.")
        else
            return answer
    }


    @GetMapping("/schedule/getBy")
    fun getByDateAndResp(@RequestHeader("Date") date: LocalDate,
                         @RequestHeader("Resp") respId: Int): ScheduleList? {
        return scheduleService.getAllByDateAndResp(date, respId)
            ?: throw ResponseStatusException(NOT_FOUND, "This date or responsibility does not exist in this table.")
    }


    @PostMapping("/schedule")
    @ResponseBody
    fun postSchedule(@RequestBody newSchedule: ScheduleList): ScheduleList? {
        if (respService.getRespById(newSchedule.responsibilityId) == null)
            throw ResponseStatusException(NOT_FOUND, "Responsibility with this id does not exist in Responsibilities table.")
        if (empService.getEmpById(newSchedule.employeeId) == null)
            throw ResponseStatusException(NOT_FOUND, "Employee with this id does not exist in Employee table.")
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
            throw ResponseStatusException(NOT_FOUND, "Responsibility with this id does not exist in Responsibilities table.")
        if (empService.getEmpById(newSchedule.employeeId) == null)
            throw ResponseStatusException(NOT_FOUND, "Employee with this id does not exist in Employee table.")
        if (scheduleService.getScheduleById(newSchedule.id) == null)
            throw ResponseStatusException(NOT_FOUND, "Schedule with this id does not exist in Schedule table.")
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
            throw ResponseStatusException(NOT_FOUND, "This date does not exist in this table.")
        else
            scheduleService.deleteSchedule(id)
    }


    @DeleteMapping("/schedule/deleteOld")
    fun deleteOldSchedules() {
        val currentDate: LocalDate = LocalDate.now()
        scheduleService.deleteOldSchedules(currentDate)
    }

}


@RestController
class RespController(val respService: ResponsibilitiesService) {
    @GetMapping("/responsibilities")
    fun getRespList(@RequestParam("ordering") orderType: String?): List<Responsibility>? =
        when (orderType) {
            "id" -> respService.ascSortById()
            "-id" -> respService.descSortById()
            "name" -> respService.ascSortByName()
            "-name" -> respService.descSortByName()
            else -> respService.getRespList()
        }



    @GetMapping("/responsibilities/{id}")
    fun getRespById(@PathVariable("id") id: Int): Responsibility?
        = respService.getRespById(id) ?: throw ResponseStatusException(NOT_FOUND, "This responsibility does not exist.")



    @PostMapping("/responsibilities")
    @ResponseBody
    fun postResp(@RequestBody newResp: Responsibility): Responsibility  {
        if (respService.db.isExists(newResp.responsibilityName, newResp.daysNumber) != null)
            throw ResponseStatusException(BAD_REQUEST, "This responsibility already exists.")
        return respService.postResp(newResp)
    }


    @DeleteMapping("/responsibilities/{id}")
    fun deleteResp(@PathVariable("id") id: Int) {
        try {
            if (respService.getRespById(id) == null)
                throw ResponseStatusException(NOT_FOUND, "This responsibility does not exist.")
            else
                respService.deleteResp(id)
        }
        catch (ex: DbActionExecutionException) {
            throw ResponseStatusException(
                BAD_REQUEST,
                "This responsibility exists in Schedule table and cannot be deleted.")
        }
    }


    @PatchMapping("/responsibilities")
    @ResponseBody
    fun updateResp(@RequestBody resp: Responsibility) {
        val id: Int = resp.responsibilityId
        val name: String = resp.responsibilityName
        val days: Int = resp.daysNumber
        if (respService.getRespById(id) == null)
            throw ResponseStatusException(NOT_FOUND, "This responsibility does not exist.")
        else {
            respService.updateResp(id, name, days)
        }
    }

    @PatchMapping("/responsibilities/clear")
    fun clearTable() {
        try {
            respService.clearTable()
        }
        catch (ex: UncategorizedSQLException) {
            throw ResponseStatusException(
                BAD_REQUEST, "These responsibilities exist in Schedule table and cannot be deleted.")
        }
    }
}


@RestController
@RequestMapping
class EmployeeController(val empService: EmployeeService) {
    @GetMapping("/employees")
    fun getEmpList(@RequestParam("ordering") orderType: String?): List<Employee>? =
        when (orderType) {
            "id" -> empService.ascSortById()
            "-id" -> empService.descSortById()
            "name" -> empService.ascSortByName()
            "-name" -> empService.descSortByName()
            else -> empService.getEmpList()
        }



    @GetMapping("/employees/{id}")
    fun getEmpById(@PathVariable("id") id: Int): Employee?
        = empService.getEmpById(id) ?: throw ResponseStatusException(NOT_FOUND, "This employee does not exist.")


    @PostMapping("/employees")
    @ResponseBody
    fun postEmp(@RequestBody emp: Employee): Employee {
        return empService.postEmp(emp)
    }


    @DeleteMapping("/employees/{id}")
    fun deleteEmp(@PathVariable("id") id: Int) {
        try {
            if (empService.getEmpById(id) == null)
                throw ResponseStatusException(NOT_FOUND, "This employee does not exist.")
            else
                empService.deleteEmp(id)
        }
        catch (ex: DbActionExecutionException) {
            throw ResponseStatusException(BAD_REQUEST, "This employee exists in Schedule table and cannot be deleted.")
        }

    }


    @PatchMapping("/employees")
    @ResponseBody
    fun updateEmp(@RequestBody emp: Employee) {
        val id: Int = emp.employeeId
        val name: String = emp.employeeName
        if (empService.getEmpById(id) == null)
            throw ResponseStatusException(NOT_FOUND, "This employee does not exist.")
        else {
            empService.updateEmp(id, name)
        }
    }

    @PatchMapping("/employee/clear")
    fun clearTable() {
        try {
            empService.clearTable()
        }
        catch (ex: UncategorizedSQLException) {
            throw ResponseStatusException(
                BAD_REQUEST, "These employees exist in Schedule table and cannot be deleted.")
        }
    }

}
