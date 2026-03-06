package com.blivtech.syncshift.data.network

import com.blivtech.syncshift.data.model.ApiResponse
import com.blivtech.syncshift.data.model.request.LoginRequest
import com.blivtech.syncshift.data.model.response.CompanyDetails
import com.blivtech.syncshift.data.model.response.LoginResponse
import com.blivtech.syncshift.ui.company.CompanySaveRequestItem
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Headers
import retrofit2.Response


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
}