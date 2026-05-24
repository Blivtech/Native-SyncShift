package com.blivtech.syncshift.data.network

import com.blivtech.syncshift.data.model.ApiResponse
import com.blivtech.syncshift.data.model.local.Entity.EmployeeEntity
import com.blivtech.syncshift.data.model.request.AdvanceRequest
import com.blivtech.syncshift.data.model.request.DayPlanRequest
import com.blivtech.syncshift.data.model.request.EmployeeRequest
import com.blivtech.syncshift.data.model.request.LoginRequest
import com.blivtech.syncshift.data.model.response.AddEmployeeResponse
import com.blivtech.syncshift.data.model.response.CompanyDetails
import com.blivtech.syncshift.data.model.response.GetEmployeeListResponse
import com.blivtech.syncshift.data.model.response.LoginResponse
import com.blivtech.syncshift.data.model.response.SaveAttendaceResponse
import com.blivtech.syncshift.ui.company.CompanySaveRequestItem
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Headers
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {
    @Headers("Accept:application/json")

    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<ApiResponse<LoginResponse>>

    @POST("company/save")
    suspend fun saveCompany(
        @Body request: CompanySaveRequestItem
    ): Response<ApiResponse<CompanyDetails>>


    @POST("employees/save")
    suspend fun saveEmployee(
        @Body employee: EmployeeEntity
    ):  Response<ApiResponse<EmployeeEntity>>


    @GET("employees/company/{companyCode}")
    suspend fun getEmployeesByCompanyCode(
        @Path("companyCode") companyCode: String
    ): Response<ApiResponse<List<EmployeeEntity>>>



    @POST("dayplan/save")
    suspend fun saveDayPlan(
        @Body request: DayPlanRequest
    ):  Response<ApiResponse<EmployeeEntity>>


    @POST("api/advance/save")
    suspend fun saveAdvance(
        @Body request:List<AdvanceRequest>

    ):Response<ApiResponse<Any>>


}