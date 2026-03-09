package com.blivtech.syncshift.data.repository

import com.blivtech.syncshift.data.model.response.UiState
import com.blivtech.syncshift.data.model.local.Dao.EmployeeDao
import com.blivtech.syncshift.data.model.local.Entity.EmployeeEntity
import com.blivtech.syncshift.data.model.request.EmployeeRequest
import com.blivtech.syncshift.data.model.response.AddEmployeeResponse
import com.blivtech.syncshift.data.network.ApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EmployeeRepository @Inject constructor(
    private val api: ApiService,   private val dao: EmployeeDao

) {

    suspend fun addEmployee(employee: EmployeeEntity): UiState<Boolean> {
        return try {
            val response = api.saveEmployee(employee)

            if (response.isSuccessful && response.body() != null) {
                val body = response.body()!!
                if( body.successCode){
                    body.response?.let { insertEmployee(it) }
                    UiState.Success(true,"")
                }else{
                    UiState.Error(body.message)
                }

            } else {
                UiState.Error("Error: ${response.code()} ${response.message()}")
            }

        } catch (e: Exception) {
            UiState.Error(e.message ?: "Unknown error")
        }
    }

    fun observeEmployees(): Flow<List<EmployeeEntity>> = dao.getEmployees()

   private  suspend  fun insertEmployee(data: EmployeeEntity) {
         dao.insertEmployee(data)

    }

    suspend fun syncEmployees(companyCode: String): UiState<Boolean> {
        return try {
            val response = api.getEmployeesByCompanyCode(companyCode)
            if (response.isSuccessful && response.body() != null) {

                val body = response.body()!!
                if( body.successCode){
                    dao.clearEmployees()
                    body.response?.let {  dao.insertEmployees(it) }
                    UiState.Success(true,body.message)
                }else{
                    UiState.Error(body.message)
                }

            } else {
                UiState.Error("API Error ${response.code()}")
            }

        } catch (e: Exception) {
            UiState.Error(e.message ?: "Unknown error")
        }
    }


}
