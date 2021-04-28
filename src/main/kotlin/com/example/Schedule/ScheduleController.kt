package com.example.Schedule

import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// MessageResource will serve the requests and return a JSON document
// containing a collection of Schedule objects
@RestController
class ScheduleController(val service: ScheduleService) {
    @GetMapping("/get_all_info")
    fun getAllInfo(): List<ScheduleList> = service.getAll()

    @GetMapping("/find_by_id/{id}")
    fun findById(@PathVariable("id") id: Int = 1): ScheduleList? = service.getById(id)

    @GetMapping("/find_by_date/{year}/{month}/{day}")
    fun findByDate(@PathVariable("year") year: String,
                   @PathVariable("month") month: String,
                   @PathVariable("day") day: String): ScheduleList? {
        var dateString = "$year-$month-$day"
        val dateDate = LocalDate.parse(dateString, DateTimeFormatter.ISO_DATE)
        return service.getEmpByDate(dateDate)
    }

    @GetMapping("/find_by_resp/{id}")
    fun findByResp(@PathVariable("id") id: Int): ScheduleList? = service.getEmpByResp(id)

    @GetMapping("/find/date/{year}/{month}/{day}/resp_id/{id}")
    fun findByDateAndResp(@PathVariable("year") year: String,
                          @PathVariable("month") month: String,
                          @PathVariable("day") day: String,
                          @PathVariable("id") id: Int): ScheduleList? {
        var dateString = "$year-$month-$day"
        val dateDate = LocalDate.parse(dateString, DateTimeFormatter.ISO_DATE)
        return service.getEmpByDateAndResp(dateDate, id)
    }

    @PostMapping
    fun post(@RequestBody info: ScheduleList) {
        service.post(info)
    }
}