package com.blivtech.syncshift.data.repository

import com.blivtech.syncshift.data.model.request.LoginRequest
import com.blivtech.syncshift.data.model.response.LoginResponse
import com.blivtech.syncshift.data.network.ApiService
import javax.inject.Inject

class LoginRepository @Inject constructor(private val api: ApiService) {

    suspend fun login(request: LoginRequest): LoginResponse {
        return api.login(request)
    }
}
