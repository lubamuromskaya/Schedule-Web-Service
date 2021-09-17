package com.example.model

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.SequenceGenerator

@Entity
class Responsibility(
    @javax.persistence.Id
    @SequenceGenerator(name = "responsibility_generator", sequenceName = "responsibility_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "responsibility_generator")
    var id: Int,

    var name: String,

    var daysNumber: Int,

    @OneToMany(cascade = [CascadeType.REMOVE], mappedBy = "responsibilityId")
    var schedules: List<Schedule> = emptyList()
)