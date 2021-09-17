package com.example.repository

import com.example.model.Employee
import org.springframework.data.repository.PagingAndSortingRepository

interface EmployeeRepository: PagingAndSortingRepository<Employee, Int> {}