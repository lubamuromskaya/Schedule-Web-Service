package com.example.repository

import com.example.model.Responsibility
import org.springframework.data.repository.PagingAndSortingRepository

interface ResponsibilityRepository: PagingAndSortingRepository<Responsibility, Int> {}