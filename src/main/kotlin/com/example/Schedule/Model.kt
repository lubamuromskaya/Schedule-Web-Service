package com.example.Schedule

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.data.annotation.Id
import org.springframework.data.jpa.repository.Temporal
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.sql.Date
import javax.persistence.TemporalType

// объектное представление данных в бд

@EntityScan
@Table("schedule")
data class ScheduleList(
    @Id
    @Column("employee_id")
    val employee_id: Int = 1,

    @Column("employee_name")
    val employee_name: String,

    @Column("responsibility_id")
    val responsibility_id: Int,

    @Column("responsibility_start")
    @Temporal(TemporalType.DATE)
    val responsibility_start: Date?,

    @Column("responsibility_end")
    @Temporal(TemporalType.DATE)
    val responsibility_end: Date?
)
