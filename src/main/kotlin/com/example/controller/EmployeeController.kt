package com.example.controller

import com.example.model.Employee
import com.example.service.EmployeeService
import org.springframework.data.relational.core.conversion.DbActionExecutionException
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.jdbc.UncategorizedSQLException
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

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
