package com.blivtech.syncshift.domain.usecase

import com.blivtech.syncshift.data.model.Resource
import com.blivtech.syncshift.data.model.request.LoginRequest
import com.blivtech.syncshift.data.model.response.LoginResponse
import com.blivtech.syncshift.data.repository.LoginRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject


class LoginUseCase @Inject constructor(private val repository: LoginRepository) {

    suspend operator fun invoke(request: LoginRequest): Resource<LoginResponse> {

        // 1. Validation
        if (request.username.isBlank()) {
            return Resource.Error("Username cannot be empty")
        }

        if (request.password.length < 4) {
            return Resource.Error("Password must be at least 4 characters")
        }

        // 2. Business rule → Lowercase username
        val finalRequest = request.copy(
            username = request.username.lowercase(),
            updated_date = getCurrentTime()
        )

        // 3. Call Repository
        return  Resource.Success(repository.login(finalRequest))
    }

    private fun getCurrentTime(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return sdf.format(Date())
    }
}
