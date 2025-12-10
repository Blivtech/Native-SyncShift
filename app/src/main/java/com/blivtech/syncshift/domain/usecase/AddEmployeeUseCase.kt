package com.blivtech.syncshift.domain.usecase

import com.blivtech.syncshift.data.model.Resource
import com.blivtech.syncshift.data.model.request.EmployeeRequest
import com.blivtech.syncshift.data.model.response.AddEmployeeResponse
import com.blivtech.syncshift.data.model.response.GetEmployeeListResponse
import com.blivtech.syncshift.data.repository.EmployeeRepository
import javax.inject.Inject

class AddEmployeeUseCase @Inject constructor(
    private val repository: EmployeeRepository
) {

    suspend  fun save(employee: EmployeeRequest): Resource<AddEmployeeResponse> {
        return repository.addEmployee(employee)
    }

    suspend  fun get(employee: EmployeeRequest): Resource<GetEmployeeListResponse> {
        return repository.getEmployeeList(employee)
    }
}
