package com.example.model

import javax.persistence.*

@Entity
class Responsibility(
    @javax.persistence.Id
    @SequenceGenerator(name = "responsibility_generator", sequenceName = "responsibility_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "responsibility_generator")
    var id: Int = 0,

    var name: String = "",

    var daysNumber: Int = 0,

    @OneToMany(cascade = [CascadeType.REMOVE], mappedBy = "responsibilityId")
    var schedules: List<Schedule> = emptyList()
)