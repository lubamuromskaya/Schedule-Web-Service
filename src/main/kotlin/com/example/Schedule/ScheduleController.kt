package com.example.Schedule

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// MessageResource will serve the requests and return a JSON document
// containing a collection of Schedule objects
@RestController
class ScheduleController(val scheduleService: ScheduleService) {
    @GetMapping("/info")
    fun getAllInfo(): List<ScheduleList>? = scheduleService.getAll()


    @GetMapping("/find_by_resp/{id}")
    fun findByResp(@PathVariable("id") id: Int): ScheduleList? = scheduleService.getEmpByResp(id)


    @GetMapping("/find_by_date/{year}/{month}/{day}")
    fun findByDate(@PathVariable("year") year: String,
                   @PathVariable("month") month: String,
                   @PathVariable("day") day: String): ScheduleList? {
        var dateString = "$year-$month-$day"
        val dateDate = LocalDate.parse(dateString, DateTimeFormatter.ISO_DATE)
        return scheduleService.getEmpByDate(dateDate)
    }


    @GetMapping("/find_by_date_and_resp/{year}/{month}/{day}/{id}")
    fun findByDateAndResp(@PathVariable("year") year: String,
                          @PathVariable("month") month: String,
                          @PathVariable("day") day: String,
                          @PathVariable("id") id: Int): ScheduleList? {
        var dateString = "$year-$month-$day"
        val dateDate = LocalDate.parse(dateString, DateTimeFormatter.ISO_DATE)
        return scheduleService.getEmpByDateAndResp(dateDate, id)
    }


    // TODO: check for correct working
    @GetMapping("/delete/emp/{emp_id}/resp/{resp_id}")
    fun deleteEmpByRespId(@PathVariable("emp_id") emp_id: Int,
                          @PathVariable("resp_id") resp_id: Int) = scheduleService.delete(emp_id, resp_id)
}

@RestController
class RespController(val respService: ResponsibilitiesService) {
    @GetMapping("/resp_list")
    fun getRespList(): List<Responsibility>? {
        return respService.getRespList()
    }

    // TODO: works incorrectly
    @GetMapping("/resp_list/id/{id}")
    fun getNameById(@PathVariable("id") id: Int): Responsibility? {
        return respService.getNameById(id)
    }

    // TODO: doesnt work
    @GetMapping("/resp_list/name/{name}")
    fun getIdByName(@PathVariable("name") name: String): Responsibility? {
        return respService.getIdByName(name)
    }

    // TODO: POST

    // TODO: DELETE
}

class EmployeeController(val empService: EmployeeService) {
    @GetMapping("/employees")
    fun getEmpList(): List<Employee>? = empService.getEmpList()

    @GetMapping("/employees/{id}")
    fun getEmpById(@PathVariable("id") id: Int): Employee? = empService.getEmpById(id)

    // TODO: POST

    // TODO: DELETE
}

@RestController
class MainController(val scheduleService: ScheduleService, val respService: ResponsibilitiesService,
                     val empService: EmployeeService) {
    // TODO: POST

    // TODO: DELETE
}