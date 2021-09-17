package com.example.service

import com.example.model.Employee
import com.example.repository.EmployeeRepository
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class EmployeeService(val db: EmployeeRepository) {
    fun getEmpById(id: Int): Employee? = db.findByIdOrNull(id)

    @Transactional(readOnly = true)
    fun getByIdSortedAsc(): List<Employee>? = db.findAll(Sort.by("id")).toList()

    @Transactional(readOnly = true)
    fun getByIdSortedDesc(): List<Employee>? = db.findAll(Sort.by(Sort.Direction.DESC, "id")).toList()

    @Transactional(readOnly = true)
    fun getByNameSortedAsc(): List<Employee>? = db.findAll(Sort.by("name")).toList()

    @Transactional(readOnly = true)
    fun getByNameSortedDesc(): List<Employee>? = db.findAll(Sort.by(Sort.Direction.DESC, "name")).toList()

    @Transactional
    fun update(id: Int, emp: Employee) = emp.apply { this.id = id }.let(db::save)

    fun post(emp: Employee): Employee = db.save(emp)

    fun delete(id: Int) = db.deleteById(id)

    fun clearTable() = db.deleteAll()
}
