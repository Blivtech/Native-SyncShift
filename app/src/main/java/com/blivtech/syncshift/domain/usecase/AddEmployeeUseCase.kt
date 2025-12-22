package com.blivtech.syncshift.domain.usecase

import com.blivtech.syncshift.data.model.Resource
import com.blivtech.syncshift.data.model.local.EmployeeEntity
import com.blivtech.syncshift.data.model.request.EmployeeRequest
import com.blivtech.syncshift.data.model.response.AddEmployeeResponse
import com.blivtech.syncshift.data.repository.EmployeeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
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

//        if (!isValidDate(employee.date_of_birth)) {
//            return Resource.Error("Invalid DOB format (yyyy-MM-dd)")
//        }
//
//        if (!isValidDate(employee.joining_date)) {
//            return Resource.Error("Invalid Joining Date format (yyyy-MM-dd)")
//        }

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


    fun observeEmployees(): Flow<List<EmployeeEntity>> = repository.observeEmployees()


    suspend fun get(btcode: String): Resource<Unit> {
        val localData = repository.observeEmployees().firstOrNull()
        if (!localData.isNullOrEmpty()) {
            return Resource.Success(Unit)
        }
        return repository.syncEmployees(btcode)
    }
}
