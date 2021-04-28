package com.example.Schedule

import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ScheduleService(val db: ScheduleRepository) {
    // for getting full Schedule table from the database
    fun getAll(): List<ScheduleList> = db.getAllInfo()

    fun getById(id: Int): ScheduleList? = db.getEmpById(id) // TODO: nullable
        //db.findById(id).orElse(null)

    fun getEmpByDate(date: LocalDate): ScheduleList? = db.getEmpByDate(date) // TODO: nullable

    fun getEmpByResp(respId: Int): ScheduleList? = db.getEmpByResp(respId) // TODO: nullable

    fun getEmpByDateAndResp(date: LocalDate, respId: Int): ScheduleList? = db.getEmpByDateAndResp(date, respId)

    // for writing a new Message object to the database:
    fun post(info: ScheduleList){
        db.save(info)
    }
}