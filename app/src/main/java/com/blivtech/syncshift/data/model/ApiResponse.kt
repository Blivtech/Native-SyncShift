package com.blivtech.syncshift.data.model

data class ApiResponse<T>(
    val successCode: Boolean,
    val message: String,
    val response: T?
)