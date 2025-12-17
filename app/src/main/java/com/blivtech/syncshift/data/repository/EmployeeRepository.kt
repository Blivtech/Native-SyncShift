package com.blivtech.syncshift.data.repository

import com.blivtech.syncshift.data.model.Resource
import com.blivtech.syncshift.data.model.local.EmployeeDao
import com.blivtech.syncshift.data.model.local.EmployeeEntity
import com.blivtech.syncshift.data.model.request.EmployeeRequest
import com.blivtech.syncshift.data.model.response.AddEmployeeResponse
import com.blivtech.syncshift.data.model.response.GetEmployeeListResponse
import com.blivtech.syncshift.data.network.ApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EmployeeRepository @Inject constructor(
    private val api: ApiService,   private val dao: EmployeeDao

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

    fun observeEmployees(): Flow<List<EmployeeEntity>> = dao.getEmployees()


    suspend fun syncEmployees(btcode: String): Resource<Unit> {
        return try {
            val response = api.getEmployees(btcode)
            if (response.isSuccessful && response.body() != null) {

                val entityList = response.body()!!.data.map {
                    it.toEntity()
                }

                dao.clearEmployees()
                dao.insertEmployees(entityList)

                Resource.Success(Unit)
            } else {
                Resource.Error("API Error ${response.code()}")
            }

        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error")
        }
    }
    private fun EmployeeRequest.toEntity(): EmployeeEntity {
        return EmployeeEntity(
            employee_id = this.employee_id,
            bt_code = this.bt_code,
            employee_name = this.employee_name,
            city = this.city,
            salary_type = this.salary_type,
            salary_code = this.salary_code,
            email = this.email,
            phone = this.phone,
            department = this.department,
            designation = this.designation,
            date_of_birth = this.date_of_birth,
            joining_date = this.joining_date,
            address = this.address,
            pincode = this.pincode,
            status = this.status
        )
    }

}
