package com.example.repository

import com.example.model.Responsibility
import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository

interface ResponsibilityRepository: CrudRepository<Responsibility, Int> {
    @Query("select * from responsibilities")
    fun getRespList(): List<Responsibility>?

    @Query("select days_number from responsibilities where responsibility_id = :id")
    fun getDaysNumberById(id: Int): Long

    @Query("select * from responsibilities where responsibility_id = :id")
    fun getRespById(id: Int): Responsibility?

    @Query("select * from responsibilities where responsibility_name = :respName and days_number = :daysNumber")
    fun isExists(respName: String, daysNumber: Int): Responsibility?

    @Query("select * from responsibilities order by responsibility_id asc")
    fun ascendingSortById(): List<Responsibility>?

    @Query("select * from responsibilities order by responsibility_id desc")
    fun descendingSortById(): List<Responsibility>?

    @Query("select * from responsibilities order by responsibility_name asc")
    fun ascendingSortByName(): List<Responsibility>?

    @Query("select * from responsibilities order by responsibility_name desc")
    fun descendingSortByName(): List<Responsibility>?

    @Modifying
    @Query("update responsibilities set responsibility_name = :name, " +
            "days_number = :days where responsibility_id = :id;")
    fun updateResp(id: Int, name: String, days: Int)

    @Modifying
    @Query("truncate table responsibilities")
    fun clearTable()
}