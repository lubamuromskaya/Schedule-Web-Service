package com.example.model

import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.SequenceGenerator

@Entity
class Schedule(
    @javax.persistence.Id
    @SequenceGenerator(name = "schedule_generator", sequenceName = "schedule_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "schedule_generator")
    var id: Int,

    @JoinColumn(name = "employee_id")
    var employeeId: Int,

    @JoinColumn(name = "responsibility_id")
    var responsibilityId: Int,

    var startDate: LocalDate,

    var endDate: LocalDate = LocalDate.now()
)