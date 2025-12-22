package com.blivtech.syncshift.data.network

import com.blivtech.syncshift.data.model.request.EmployeeRequest
import com.blivtech.syncshift.data.model.request.LoginRequest
import com.blivtech.syncshift.data.model.response.AddEmployeeResponse
import com.blivtech.syncshift.data.model.response.GetEmployeeListResponse
import com.blivtech.syncshift.data.model.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Headers
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @Headers("Accept:application/json")

    @POST("?action=login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

    @POST("?action=save_employee") // ← change if needed
    suspend fun addEmployee(
        @Body employee: EmployeeRequest
    ): Response<AddEmployeeResponse>

    @GET("?action=get_employees")
    suspend fun getEmployees(@Query("bt_code") btcode: String): Response<GetEmployeeListResponse>

}