package com.example.service

import com.example.model.Employee
import com.example.repository.EmployeeRepository
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class EmployeeService(val db: EmployeeRepository) {
    fun getEmpById(id: Int): Employee? = db.findByIdOrNull(id)

    fun ascSortById(): List<Employee>? = db.findAll(Sort.by("id")).toList()

    fun descSortById(): List<Employee>? = db.findAll(Sort.by(Sort.Direction.DESC, "id")).toList()

    fun ascSortByName(): List<Employee>? = db.findAll(Sort.by("name")).toList()

    fun descSortByName(): List<Employee>? = db.findAll(Sort.by(Sort.Direction.DESC, "name")).toList()

    fun updateEmp(id: Int, emp: Employee) = emp.apply { this.id = id }.let(db::save)

    fun clearTable() = db.deleteAll()

    fun postEmp(emp: Employee): Employee = db.save(emp)

    fun deleteEmp(id: Int) = db.deleteById(id)
}
