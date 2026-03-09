package com.blivtech.syncshift.data.model.request

data class LoginRequest(
    val userName: String,
    val password: String,
    val mode: String,
    val appVersion: String,
)
