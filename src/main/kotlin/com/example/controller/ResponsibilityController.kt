package com.example.controller

import com.example.model.Responsibility
import com.example.service.ResponsibilityService
import org.springframework.data.relational.core.conversion.DbActionExecutionException
import org.springframework.http.HttpStatus
import org.springframework.jdbc.UncategorizedSQLException
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
class ResponsibilityController(val respService: ResponsibilityService) {
    @GetMapping("/responsibilities")
    fun getRespList(@RequestParam("ordering") orderType: String?): List<Responsibility>? =
        when (orderType) {
            "id" -> respService.ascSortById()
            "-id" -> respService.descSortById()
            "name" -> respService.ascSortByName()
            "-name" -> respService.descSortByName()
            else -> respService.getRespList()
        }

    @GetMapping("/responsibilities/{id}")
    fun getRespById(@PathVariable("id") id: Int): Responsibility?
            = respService.getRespById(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "This responsibility does not exist.")

    @PostMapping("/responsibilities")
    @ResponseBody
    fun postResp(@RequestBody newResp: Responsibility): Responsibility {
        if (respService.db.isExists(newResp.responsibilityName, newResp.daysNumber) != null)
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "This responsibility already exists.")
        return respService.postResp(newResp)
    }

    @DeleteMapping("/responsibilities/{id}")
    fun deleteResp(@PathVariable("id") id: Int) {
        try {
            if (respService.getRespById(id) == null)
                throw ResponseStatusException(HttpStatus.NOT_FOUND, "This responsibility does not exist.")
            else
                respService.deleteResp(id)
        }
        catch (ex: DbActionExecutionException) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "This responsibility exists in Schedule table and cannot be deleted.")
        }
    }

    @PatchMapping("/responsibilities")
    @ResponseBody
    fun updateResp(@RequestBody resp: Responsibility) {
        val id: Int = resp.responsibilityId
        val name: String = resp.responsibilityName
        val days: Int = resp.daysNumber
        if (respService.getRespById(id) == null)
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "This responsibility does not exist.")
        else {
            respService.updateResp(id, name, days)
        }
    }

    @PatchMapping("/responsibilities/clear")
    fun clearTable() {
        try {
            respService.clearTable()
        }
        catch (ex: UncategorizedSQLException) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST, "These responsibilities exist in Schedule table and cannot be deleted.")
        }
    }
}