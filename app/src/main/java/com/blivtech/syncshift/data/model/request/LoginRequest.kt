package com.blivtech.syncshift.data.model.request

data class LoginRequest(
    val username: String,
    val password: String,
    val mode: String,
    val app_version: String,
    val updated_date: String
)
