package com.blivtech.syncshift.data.model

import com.google.gson.JsonElement

sealed class ApiState {
    object Loading : ApiState()
    data class Success(val response: JsonElement?) : ApiState()
    data class Error(val message: String) : ApiState()
}