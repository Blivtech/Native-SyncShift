package com.blivtech.syncshift.data.model.response

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val data: LoginData?
)

data class LoginData(
    val bt_code: String,
    val username: String,
    val name: String,
    val mobile_number: String,
    val address: String,
    val category: String,
    val account_type: String,
    val app_version: String,
    val active_date: String
)
