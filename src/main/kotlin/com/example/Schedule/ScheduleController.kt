package com.example.Schedule

import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RestController
class ScheduleController(val scheduleService: ScheduleService,
                         val respService: ResponsibilitiesService,
                         val empService: EmployeeService) {
    @GetMapping("/schedule")
    fun getAllInfo(): List<ScheduleList>? = scheduleService.getAllInfo()


    @GetMapping("/schedule/getById")
    fun findBySchdId(@RequestParam("id") id: Int): ScheduleList? {
        return scheduleService.getByScheduleId(id)
            ?: throw ResponseStatusException(NOT_FOUND, "Schedule with this id does not exist.")
    }


    @GetMapping("/schedule/getEmp")
    fun findByEmpId(@RequestParam("id") id: Int): List<ScheduleList>? {
        return scheduleService.getAllByEmpId(id)
            ?: throw ResponseStatusException(NOT_FOUND, "This employee does not exist in this table.")
    }


    @GetMapping("/schedule/getResp")
    fun findByResp(@RequestParam("id") id: Int): List<ScheduleList>? {
        return scheduleService.getEmpByResp(id)
            ?: throw ResponseStatusException(NOT_FOUND, "This responsibility does not exist in this table.")
    }


    @GetMapping("/schedule/getByDate")
    fun findByDate(@RequestParam("date") date: LocalDate): List<ScheduleList>? {
        return scheduleService.getEmpByDate(date)
            ?: throw ResponseStatusException(NOT_FOUND, "This date does not exist in this table.")
    }


    // TODO: change request type to json - ?
    @GetMapping("/schedule/resp/{id}/date/{year}/{month}/{day}/{id}")
    fun findByDateAndResp(@PathVariable("year") year: String,
                          @PathVariable("month") month: String,
                          @PathVariable("day") day: String,
                          @PathVariable("id") id: Int): ScheduleList? {
        var dateString = "$year-$month-$day"
        val dateDate = LocalDate.parse(dateString, DateTimeFormatter.ISO_DATE)
        return scheduleService.getEmpByDateAndResp(dateDate, id)
    }


    @PostMapping("/schedule")
    @ResponseBody
    fun postSchedule(@RequestBody newSchedule: ScheduleList): ScheduleList? {
        if (respService.getRespById(newSchedule.responsibility_id) == null)
            throw ResponseStatusException(NOT_FOUND, "Responsibility with this id does not exist in Responsibilities table.")
        if (empService.getEmpById(newSchedule.employee_id) == null)
            throw ResponseStatusException(NOT_FOUND, "Employee with this id does not exist in Employee table.")
        else {
            val daysNumber: Long = respService.getDaysNumberById(newSchedule.responsibility_id)
            newSchedule.responsibility_end = newSchedule.responsibility_start.plusDays(daysNumber)
            return scheduleService.postSchedule(newSchedule)
        }
    }

    @PatchMapping("/schedule")
    @ResponseBody
    fun updateSchedule(@RequestBody newSchedule: ScheduleList) {
        if (respService.getRespById(newSchedule.responsibility_id) == null)
            throw ResponseStatusException(NOT_FOUND, "Responsibility with this id does not exist in Responsibilities table.")
        if (empService.getEmpById(newSchedule.employee_id) == null)
            throw ResponseStatusException(NOT_FOUND, "Employee with this id does not exist in Employee table.")
        if (scheduleService.getByScheduleId(newSchedule.id) == null)
            throw ResponseStatusException(NOT_FOUND, "Schedule with this id does not exist in Schedule table.")
        else {
            val daysNumber: Long = respService.getDaysNumberById(newSchedule.responsibility_id)
            newSchedule.responsibility_end = newSchedule.responsibility_start.plusDays(daysNumber)

            return scheduleService.updateSchedule(newSchedule.id, newSchedule.employee_id,
                                                    newSchedule.responsibility_id,
                                                    newSchedule.responsibility_start,
                                                    newSchedule.responsibility_end)
        }
    }


    @PatchMapping("/schedule/clear")
    fun clearTable() {
        scheduleService.clearTable()
    }


    @DeleteMapping("/schedule")
    fun deleteSchedule(@RequestParam("id") id: Int) {
        if (scheduleService.getByScheduleId(id) == null)
            throw ResponseStatusException(NOT_FOUND, "This date does not exist in this table.")
        else
            scheduleService.deleteSchedule(id)
    }


    @DeleteMapping("/schedule/deleteOld")
    fun deleteOldSchedules() {
        val currentDate = LocalDate.now()
        scheduleService.deleteOldSchedules(currentDate)
    }

}


@RestController
class RespController(val respService: ResponsibilitiesService) {
    @GetMapping("/responsibilities")
    fun getRespList(@RequestParam("ordering") orderType: String?): List<Responsibility>? {
        if (orderType.isNullOrEmpty())
            return respService.ascSortById()
        if (orderType.contentEquals("id"))
            return respService.ascSortById()
        if (orderType.contentEquals("-id"))
            return respService.descSortById()
        if (orderType.contentEquals("name"))
            return respService.ascSortByName()
        if (orderType.contentEquals("-name"))
            return respService.descSortByName()
        return respService.getRespList()
    }


    @GetMapping("/responsibilities/{id}")
    fun getRespById(@PathVariable("id") id: Int): Responsibility?
        = respService.getRespById(id) ?: throw ResponseStatusException(NOT_FOUND, "This responsibility does not exist.")



    @PostMapping("/responsibilities")
    @ResponseBody
    fun postResp(@RequestBody newResp: Responsibility): Responsibility  {
        if (respService.db.isExists(newResp.responsibility_name, newResp.days_number) != null)
            throw ResponseStatusException(BAD_REQUEST, "This responsibility is already exists.")
        return respService.postResp(newResp)
    }


    @DeleteMapping("/responsibilities/{id}")
    fun deleteResp(@PathVariable("id") id: Int) {
        if (respService.getRespById(id) == null)
            throw ResponseStatusException(NOT_FOUND, "This responsibility does not exist.")
        else
            respService.deleteResp(id)
    }


    @PatchMapping("/responsibilities")
    @ResponseBody
    fun updateResp(@RequestBody resp: Responsibility) {
        val id: Int = resp.responsibility_id
        val name: String = resp.responsibility_name
        val days: Int = resp.days_number
        if (respService.getRespById(id) == null)
            throw ResponseStatusException(NOT_FOUND, "This responsibility does not exist")
        else {
            respService.updateResp(id, name, days)
        }
    }

    @PatchMapping("/responsibilities/clear")
    fun clearTable() {
        respService.clearTable()
    }
}


@RestController
@RequestMapping
class EmployeeController(val empService: EmployeeService) {
    @GetMapping("/employees")
    fun getEmpList(@RequestParam("ordering") orderType: String?): List<Employee>? {
        if (orderType.isNullOrEmpty())
            return empService.ascSortById()
        if (orderType.contentEquals("id"))
            return empService.ascSortById()
        if (orderType.contentEquals("-id"))
            return empService.descSortById()
        if (orderType.contentEquals("name"))
            return empService.ascSortByName()
        if (orderType.contentEquals("-name"))
            return empService.descSortByName()
        return empService.getEmpList()
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
        if (empService.getEmpById(id) == null)
            throw ResponseStatusException(NOT_FOUND, "This employee does not exist.")
        else
            empService.deleteEmp(id)
    }


    @PatchMapping("/employees")
    @ResponseBody
    fun updateEmp(@RequestBody emp: Employee) {
        val id: Int = emp.employee_id
        val name: String = emp.employee_name
        if (empService.getEmpById(id) == null)
            throw ResponseStatusException(NOT_FOUND, "This employee does not exist.")
        else {
            empService.updateEmp(id, name)
        }
    }

    @PatchMapping("/employee/clear")
    fun clearTable() {
        empService.clearTable()
    }

}
