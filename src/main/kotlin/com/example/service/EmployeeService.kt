package com.example.service

import com.example.model.Employee
import com.example.repository.EmployeeRepository
import org.springframework.stereotype.Service

@Service
class EmployeeService(val db: EmployeeRepository) {
    fun getEmpList(): List<Employee>? = db.getEmpList()

    fun getEmpById(id: Int): Employee? = db.getEmpById(id)

    fun ascSortById(): List<Employee>? = db.ascendingSortById()

    fun descSortById(): List<Employee>? = db.descendingSortById()

    fun ascSortByName(): List<Employee>? = db.ascendingSortByName()

    fun descSortByName(): List<Employee>? = db.descendingSortByName()

    fun updateEmp(id: Int, name: String) = db.updateEmp(id, name)

    fun clearTable() = db.clearTable()

    fun postEmp(emp: Employee): Employee = db.save(emp)

    fun deleteEmp(id: Int) = db.deleteById(id)
}
