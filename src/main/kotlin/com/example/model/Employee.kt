package com.example.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType

@Table("employee")
data class Employee(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column("employee_id")
    var employeeId: Int = 0,

    @Column("employee_name")
    val employeeName: String
)