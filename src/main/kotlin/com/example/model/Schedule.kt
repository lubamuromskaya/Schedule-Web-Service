package com.example.model

import org.springframework.data.annotation.Id
import org.springframework.data.jpa.repository.Temporal
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.TemporalType

@Table("schedule")
data class ScheduleList(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column("id")
    val id: Int = 0,

    @Column("employee_id")
    val employeeId: Int,

    @Column("responsibility_id")
    val responsibilityId: Int,

    @Column("responsibility_start")
    @Temporal(TemporalType.DATE)
    var responsibilityStart: LocalDate,

    @Column("responsibility_end")
    @Temporal(TemporalType.DATE)
    var responsibilityEnd: LocalDate = LocalDate.now()
)