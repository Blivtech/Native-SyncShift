package com.blivtech.syncshift.data.model.request

import java.math.BigDecimal
import java.time.LocalDate



data class EmployeeRequest(
            private val companyCode: String,
            private val employeeName: String,
            private val address: String,
            private val joiningDate: LocalDate,
            private val dateOfBirth: LocalDate,
            private val employeeType: String,
            private val department: String,
            private val designation: String,
            private val mobileNumber: String,
            private val email: String,
            private val gender: String,
            private val district: String,
            private val taluk: String,
            private val state: String,
            private val mode: String,
            private val salaryCode: String,
            private val salary_type: String,
            private val basicSalary: BigDecimal,
            private val leaveCount: Int,
)

