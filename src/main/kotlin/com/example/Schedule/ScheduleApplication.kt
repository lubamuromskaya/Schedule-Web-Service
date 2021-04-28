package com.example.Schedule

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.*

// SpringBootApplication: включает сканирование компонентов, автоконфигурацию
// и показывает разным компонентам Spring что это Spring Boot приложение
@SpringBootApplication
class ScheduleApplication

fun main(args: Array<String>) {
	runApplication<ScheduleApplication>(*args)
	// создает контекст приложения, который конфигурируется с помощью аннотаций
}
