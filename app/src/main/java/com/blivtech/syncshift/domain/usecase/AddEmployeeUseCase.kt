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


    suspend fun save(employee: EmployeeEntity): UiState<Boolean> {

        if (employee.employeeName.isBlank()) {
            return UiState.Error("Employee name is required")
        }

        if (employee.mobileNumber.length != 10) {
            return UiState.Error("Enter valid mobile number")
        }

        if (employee.salaryType.isBlank()) {
            return UiState.Error("Select salary type")
        }

        if (employee.dateOfBirth.isBlank()) {
            return UiState.Error("Select date of birth")
        }

        if (employee.joiningDate.isBlank()) {
            return UiState.Error("Select joining date")
        }

        val salaryCode = when (employee.salaryType.lowercase()) {
            "daily" -> "1"
            "weekly" -> "2"
            "monthly" -> "3"
            else -> return UiState.Error("Invalid salary type")
        }

        val finalRequest = employee.copy(
            salaryCode = salaryCode
        )

        return repository.addEmployee(finalRequest)
    }


    fun observeEmployees(): Flow<List<EmployeeEntity>> = repository.observeEmployees()


    suspend fun get(companyCode: String): UiState<Boolean> {
        return repository.syncEmployees(companyCode = companyCode)
    }
}
