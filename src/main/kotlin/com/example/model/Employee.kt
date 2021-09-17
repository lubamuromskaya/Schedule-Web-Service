package com.example.model

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.SequenceGenerator

@Entity
class Employee(
    @javax.persistence.Id
    @SequenceGenerator(name = "employee_generator", sequenceName = "employee_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "employee_generator")
    var id: Int,

    var name: String,

    @OneToMany(cascade = [CascadeType.REMOVE], mappedBy = "employeeId")
    var schedules: List<Schedule> = emptyList()
)