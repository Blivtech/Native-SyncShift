package com.blivtech.syncshift.data.network

import com.blivtech.syncshift.data.model.request.EmployeeRequest
import com.blivtech.syncshift.data.model.request.LoginRequest
import com.blivtech.syncshift.data.model.response.AddEmployeeResponse
import com.blivtech.syncshift.data.model.response.GetEmployeeListResponse
import com.blivtech.syncshift.data.model.response.LoginResponse
import retrofit2.http.GET
import retrofit2.http.Url
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Headers
import com.google.gson.JsonObject
import com.google.gson.JsonElement
import retrofit2.Response

interface ApiService {
    @Headers("Accept:application/json")
    @GET
  suspend  fun getData(@Url url: String): Response<JsonElement>

    @POST
   suspend fun postData(@Url url: String, @Body jsonObject: JsonObject): Response<JsonElement>

    @POST("?action=login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

    @POST("?action=save_employee") // ← change if needed
    suspend fun addEmployee(
        @Body employee: EmployeeRequest
    ): Response<AddEmployeeResponse>

    @POST("?action=get_employees")   // <-- Change to correct endpoint
    suspend fun getEmployees(@Body employee: EmployeeRequest): Response<GetEmployeeListResponse>
}