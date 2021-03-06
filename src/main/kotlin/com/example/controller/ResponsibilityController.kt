package com.example.controller

import com.example.model.Responsibility
import com.example.service.ResponsibilityService
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/responsibility")
class ResponsibilityController(val respService: ResponsibilityService) {
    @GetMapping
    fun getRespList(@RequestParam("ordering") orderType: String?): List<Responsibility>? =
        when (orderType) {
            "id" -> respService.getByIdSortedAsc()
            "-id" -> respService.getByIdSortedDesc()
            "name" -> respService.getByNameSortedAsc()
            "-name" -> respService.getByNameSortedDesc()
            else -> respService.getByIdSortedAsc()
        }

    @GetMapping("/{id}")
    fun getRespById(@PathVariable("id") id: Int): Responsibility?
            = respService.getRespById(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "This responsibility does not exist.")

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    fun postResp(@RequestBody newResp: Responsibility): Responsibility {
        try {
            return respService.post(newResp)
        } catch (ex: DataIntegrityViolationException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "This responsibility already exists.")
        }
    }

    @DeleteMapping("/{id}")
    fun deleteResp(@PathVariable("id") id: Int) {
        if (respService.getRespById(id) == null)
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "This responsibility does not exist.")
        else
            respService.delete(id)
    }

    @PatchMapping("/{id}")
    fun updateResp(@PathVariable("id") id: Int, @RequestBody resp: Responsibility) {
        if (respService.getRespById(id) == null)
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "This responsibility does not exist.")
        else {
            respService.update(id, resp)
        }
    }

    @PatchMapping("/clear")
    fun clearTable() {
        respService.clearTable()
    }
}