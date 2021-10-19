package com.example.model

import javax.persistence.*

@Entity
class Employee(
    @javax.persistence.Id
    @SequenceGenerator(name = "employee_generator", sequenceName = "employee_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "employee_generator")
    var id: Int = 0,

    var name: String = "",

    @OneToMany(cascade = [CascadeType.REMOVE], mappedBy = "employeeId")
    var schedules: List<Schedule> = emptyList()
)