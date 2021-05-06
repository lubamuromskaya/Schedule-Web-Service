package com.example.Schedule

import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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


    @DeleteMapping("/responsibilities/{id}")
    fun deleteResp(@PathVariable("id") id: Int) {
        if (respService.getRespById(id) == null)
            throw ResponseStatusException(NOT_FOUND, "This responsibility does not exist")
        else
            respService.deleteResp(id)
    }


    @PatchMapping("/responsibilities")
    @ResponseBody
    fun updateEmp(@RequestBody resp: Responsibility) {
        val id: Int = resp.responsibility_id
        val name: String = resp.responsibility_name
        if (respService.getRespById(id) == null)
            throw ResponseStatusException(NOT_FOUND, "This responsibility does not exist")
        else {
            respService.updateResp(id, name)
        }
    }
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


    @DeleteMapping("/employees/{id}")
    fun deleteEmp(@PathVariable("id") id: Int) {
        if (empService.getEmpById(id) == null)
            throw ResponseStatusException(NOT_FOUND, "This employee does not exist")
        else
            empService.deleteEmp(id)
    }


    @PatchMapping("/employees")
    @ResponseBody
    fun updateEmp(@RequestBody emp: Employee) {
        val id: Int = emp.employee_id
        val name: String = emp.employee_name
        if (empService.getEmpById(id) == null)
            throw ResponseStatusException(NOT_FOUND, "This employee does not exist")
        else {
            empService.updateEmp(id, name)
        }
    }


    @GetMapping("/employees/ordering")
    fun sort(@RequestParam("by") orderType: String): List<Employee>? {
        if (orderType.contentEquals("asc"))
            return empService.ascSortById()
        else if (orderType.contentEquals("desc"))
            return empService.descSortById()

        return empService.getEmpList()
    }



    /*
    @GetMapping("/employees/ordering/{order}")
    fun sort(@PathVariable("order") orderType: String): List<Employee>? {
        if (orderType.contentEquals("asc"))
            return empService.ascSortById()
        else if (orderType.contentEquals("desc"))
            return empService.descSortById()

        return empService.getEmpList()
    }

    */
}
