package com.blivtech.syncshift.domain.usecase

import com.blivtech.syncshift.data.model.Resource
import com.blivtech.syncshift.data.model.request.EmployeeRequest
import com.blivtech.syncshift.data.model.response.AddEmployeeResponse
import com.blivtech.syncshift.data.model.response.GetEmployeeListResponse
import com.blivtech.syncshift.data.repository.EmployeeRepository
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class AddEmployeeUseCase @Inject constructor(
    private val repository: EmployeeRepository
) {

    suspend fun save(employee: EmployeeRequest): Resource<AddEmployeeResponse> {

        if (employee.employee_name.isBlank()) {
            return Resource.Error("Employee name is required")
        }

        if (employee.phone.length != 10) {
            return Resource.Error("Enter valid mobile number")
        }

        if (employee.salary_type.isBlank()) {
            return Resource.Error("Select salary type")
        }

        if (employee.date_of_birth.isBlank()) {
            return Resource.Error("Select date of birth")
        }

        if (employee.joining_date.isBlank()) {
            return Resource.Error("Select joining date")
        }

        if (!isValidDate(employee.date_of_birth)) {
            return Resource.Error("Invalid DOB format (yyyy-MM-dd)")
        }

        if (!isValidDate(employee.joining_date)) {
            return Resource.Error("Invalid Joining Date format (yyyy-MM-dd)")
        }

        val salaryCode = when (employee.salary_type.lowercase()) {
            "daily" -> "1"
            "weekly" -> "2"
            "monthly" -> "3"
            else -> return Resource.Error("Invalid salary type")
        }

        val finalRequest = employee.copy(
            salary_code = salaryCode
        )

        return repository.addEmployee(finalRequest)
    }

    suspend fun get(employee: EmployeeRequest): Resource<GetEmployeeListResponse> {
        return repository.getEmployeeList(employee)
    }

    private fun isValidDate(date: String): Boolean {
        return try {
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            sdf.isLenient = false
            sdf.parse(date)
            true
        } catch (e: Exception) {
            false
        }
    }
}
