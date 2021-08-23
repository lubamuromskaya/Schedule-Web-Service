package com.example.repository

import com.example.model.Employee
import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository

interface EmployeeRepository: CrudRepository<Employee, Int> {
    @Query("select * from employee")
    fun getEmpList(): List<Employee>?

    @Query("select * from employee where employee_id = :id;")
    fun getEmpById(id: Int): Employee?

    @Query("select * from employee order by employee_id asc")
    fun ascendingSortById(): List<Employee>?

    @Query("select * from employee order by employee_id desc")
    fun descendingSortById(): List<Employee>?

    @Query("select * from employee order by employee_name asc")
    fun ascendingSortByName(): List<Employee>?

    @Query("select * from employee order by employee_name desc")
    fun descendingSortByName(): List<Employee>?

    @Modifying
    @Query("update employee set employee_name = :name where employee_id = :id;")
    fun updateEmp(id: Int, name: String)

    @Modifying
    @Query("truncate table employee")
    fun clearTable()

}