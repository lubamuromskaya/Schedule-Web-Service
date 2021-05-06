package com.example.Schedule

import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// MessageResource will serve the requests and return a JSON document
// containing a collection of Schedule objects
@RestController
class ScheduleController(val scheduleService: ScheduleService) {
    @GetMapping("/schedule")
    fun getAllInfo(): List<ScheduleList>? = scheduleService.getAll()


    // TODO: change request type to json - ?
    @GetMapping("/schedule/resp/{id}")
    fun findByResp(@PathVariable("id") id: Int): ScheduleList? = scheduleService.getEmpByResp(id)


    // TODO: change request type to json - ?
    @GetMapping("/schedule/date/{year}/{month}/{day}")
    fun findByDate(@PathVariable("year") year: String,
                   @PathVariable("month") month: String,
                   @PathVariable("day") day: String): ScheduleList? {
        var dateString = "$year-$month-$day"
        val dateDate = LocalDate.parse(dateString, DateTimeFormatter.ISO_DATE)
        return scheduleService.getEmpByDate(dateDate)
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



    // TODO: update (patch)
}


@RestController
class RespController(val respService: ResponsibilitiesService) {
    @GetMapping("/responsibilities")
    fun getRespList(): List<Responsibility>? = respService.getRespList()


    @GetMapping("/responsibilities/{id}")
    fun getRespById(@PathVariable("id") id: Int): Responsibility?
        = respService.getRespById(id) ?: throw ResponseStatusException(NOT_FOUND, "This responsibility does not exist")


    @PostMapping("/responsibilities")
    @ResponseBody
    fun postNewResp(@RequestBody newResp: Responsibility): Responsibility = respService.postNewResp(newResp)


    @DeleteMapping("responsibilities/{id}")
    fun deleteResp(@PathVariable("id") id: Int) = respService.deleteResp(id)

    // TODO: update (patch)
}


@RestController
@RequestMapping
class EmployeeController(val empService: EmployeeService) {
    @GetMapping("/employees")
    fun getEmpList(): List<Employee>? = empService.getEmpList()


    @GetMapping("/employees/{id}")
    fun getEmpById(@PathVariable("id") id: Int): Employee?
        = empService.getEmpById(id) ?: throw ResponseStatusException(NOT_FOUND, "This employee does not exist")


    @PostMapping("/employees")
    @ResponseBody
    fun postNewEmp(@RequestBody emp: Employee): Employee {
        return empService.postNewEmp(emp)
    }


    @DeleteMapping("employees/{id}")
    fun deleteEmp(@PathVariable("id") id: Int) = empService.deleteEmp(id)


    // TODO: update (patch)
}
