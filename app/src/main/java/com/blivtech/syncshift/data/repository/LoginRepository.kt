package com.blivtech.syncshift.data.repository

import com.blivtech.syncshift.data.model.ApiResponse
import com.blivtech.syncshift.data.model.request.LoginRequest
import com.blivtech.syncshift.data.model.response.LoginResponse
import com.blivtech.syncshift.data.model.response.UiState
import com.blivtech.syncshift.data.network.ApiService
import javax.inject.Inject

class LoginRepository @Inject constructor(private val api: ApiService) {

    suspend fun login(
        request: LoginRequest
    ): UiState<LoginResponse> {

        return try {

            val response = api.login(request)
            if (response.isSuccessful) {   // ✅ checks 200–299
                val body = response.body()
                if (body != null) {
                    if (body.successCode) {
                        UiState.Success(body.response!!,body.message)
                    } else {
                        UiState.Error(body.message)
                    }
                } else {
                    UiState.Error("Empty response body")
                }
            } else {
                // ❌ HTTP error like 400, 401, 404, 500
                UiState.Error(
                    "HTTP ${response.code()} - ${response.message()}"
                )
            }
        } catch (e: Exception) {
            UiState.Error(e.message ?: "Network Error")
        }
    }
}
