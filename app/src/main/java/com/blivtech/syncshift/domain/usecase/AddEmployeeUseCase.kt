package com.blivtech.syncshift.domain.usecase

import com.blivtech.syncshift.data.model.response.UiState
import com.blivtech.syncshift.data.model.local.Entity.EmployeeEntity
import com.blivtech.syncshift.data.model.request.EmployeeRequest
import com.blivtech.syncshift.data.model.response.AddEmployeeResponse
import com.blivtech.syncshift.data.repository.EmployeeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class AddEmployeeUseCase @Inject constructor(
    private val repository: EmployeeRepository
) {


    suspend fun save(employee: EmployeeRequest): UiState<AddEmployeeResponse> {

        if (employee.employee_name.isBlank()) {
            return UiState.Error("Employee name is required")
        }

        if (employee.phone.length != 10) {
            return UiState.Error("Enter valid mobile number")
        }

        if (employee.salary_type.isBlank()) {
            return UiState.Error("Select salary type")
        }

        if (employee.date_of_birth.isBlank()) {
            return UiState.Error("Select date of birth")
        }

        if (employee.joining_date.isBlank()) {
            return UiState.Error("Select joining date")
        }

        val salaryCode = when (employee.salary_type.lowercase()) {
            "daily" -> "1"
            "weekly" -> "2"
            "monthly" -> "3"
            else -> return UiState.Error("Invalid salary type")
        }

        val finalRequest = employee.copy(
            salary_code = salaryCode
        )

        return repository.addEmployee(finalRequest)
    }


    fun observeEmployees(): Flow<List<EmployeeEntity>> = repository.observeEmployees()


    suspend fun get(btcode: String): UiState<Unit> {
        val localData = repository.observeEmployees().firstOrNull()
        if (!localData.isNullOrEmpty()) {
            return UiState.Success(Unit,"")
        }
        return repository.syncEmployees(btcode)
    }
}
