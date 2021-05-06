package com.example.Schedule

import org.springframework.data.annotation.Id
import org.springframework.data.jpa.repository.Temporal
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.sql.Date
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.TemporalType

// объектное представление данных в бд

@Table("schedule")
data class ScheduleList(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column("employee_id")
    val employee_id: Int = 0,

    @Column("responsibility_id")
    val responsibility_id: Int,

    @Column("responsibility_start")
    @Temporal(TemporalType.DATE)
    val responsibility_start: Date,

    @Column("responsibility_end")
    @Temporal(TemporalType.DATE)
    val responsibility_end: Date
)

@Table("responsibilities")
data class Responsibility(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column("responsibility_id")
    val responsibility_id: Int = 0,

    @Column("responsibility_name")
    val responsibility_name: String
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