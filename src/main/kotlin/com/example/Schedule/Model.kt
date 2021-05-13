package com.example.Schedule

import org.springframework.data.annotation.Id
import org.springframework.data.jpa.repository.Temporal
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.TemporalType

// объектное представление данных в бд

@Table("schedule")
data class ScheduleList(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column("id")
    val id: Int = 0,

    @Column("employee_id")
    val employee_id: Int,

    @Column("responsibility_id")
    val responsibility_id: Int,

    @Column("responsibility_start")
    @Temporal(TemporalType.DATE)
    var responsibility_start: LocalDate,

    @Column("responsibility_end")
    @Temporal(TemporalType.DATE)
    var responsibility_end: LocalDate = LocalDate.now()
)

@Table("responsibilities")
data class Responsibility(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column("responsibility_id")
    val responsibility_id: Int = 0,

    @Column("responsibility_name")
    val responsibility_name: String,

    @Column("days_number")
    val days_number: Int
)


@Table("employee")
data class Employee(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column("employee_id")
    var employee_id: Int = 0,

    @Column("employee_name")
    val employee_name: String
)