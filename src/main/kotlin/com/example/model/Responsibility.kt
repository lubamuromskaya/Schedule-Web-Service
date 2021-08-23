package com.example.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType

@Table("responsibilities")
data class Responsibility(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column("responsibility_id")
    val responsibilityId: Int = 0,

    @Column("responsibility_name")
    val responsibilityName: String,

    @Column("days_number")
    val daysNumber: Int
)