package com.example.controller

import com.example.model.Employee
import com.example.service.EmployeeService
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/employee")
class EmployeeController(val empService: EmployeeService) {
    @GetMapping
    fun getEmpList(@RequestParam("ordering") orderType: String?): List<Employee>? =
        when (orderType) {
            "id" -> empService.getByIdSortedAsc()
            "-id" -> empService.getByIdSortedDesc()
            "name" -> empService.getByNameSortedAsc()
            "-name" -> empService.getByNameSortedDesc()
            else -> empService.getByIdSortedAsc()
        }

    @GetMapping("/{id}")
    fun getEmpById(@PathVariable("id") id: Int): Employee?
        = empService.getEmpById(id) ?: throw ResponseStatusException(NOT_FOUND, "This employee does not exist.")

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    fun postEmp(@RequestBody emp: Employee): Employee {
        return empService.post(emp)
    }

    @DeleteMapping("/{id}")
    fun deleteEmp(@PathVariable("id") id: Int) {
        if (empService.getEmpById(id) == null)
            throw ResponseStatusException(NOT_FOUND, "This employee does not exist.")
        else
            empService.delete(id)
    }

    @PatchMapping("/{id}")
    fun updateEmp(@PathVariable("id") id: Int, @RequestBody emp: Employee) {
        if (empService.getEmpById(id) == null)
            throw ResponseStatusException(NOT_FOUND, "This employee does not exist.")
        else {
            empService.update(id, emp)
        }
    }

    @PatchMapping("/clear")
    fun clearTable() {
        empService.clearTable()
    }
}
