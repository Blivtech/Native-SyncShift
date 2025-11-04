package com.blivtech.syncshift.data.model.local




sealed class ApiState {
    object Loading : ApiState()
    data class Success(val data: String) : ApiState()
    data class Error(val message: String) : ApiState()
}