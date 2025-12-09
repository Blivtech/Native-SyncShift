package com.blivtech.syncshift.data.repository

import com.blivtech.syncshift.data.model.Resource
import com.blivtech.syncshift.data.model.request.EmployeeRequest
import com.blivtech.syncshift.data.model.response.AddEmployeeResponse
import com.blivtech.syncshift.data.model.response.GetEmployeeListResponse
import com.blivtech.syncshift.data.network.ApiService
import javax.inject.Inject

class EmployeeRepository @Inject constructor(
    private val api: ApiService
) {

    suspend fun addEmployee(employee: EmployeeRequest): Resource<AddEmployeeResponse> {
        return try {
            val response = api.addEmployee(employee)

            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error("Error: ${response.code()} ${response.message()}")
            }

        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error")
        }
    }

    suspend fun getEmployeeList(employee: EmployeeRequest): Resource<GetEmployeeListResponse> {
        return try {
            val response = api.getEmployees(employee)

            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error("API Error: ${response.code()}")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error")
        }
    }
}
