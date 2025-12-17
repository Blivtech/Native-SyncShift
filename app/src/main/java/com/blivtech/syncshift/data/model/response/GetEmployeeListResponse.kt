package com.blivtech.syncshift.data.model.response

import com.blivtech.syncshift.data.model.request.EmployeeRequest

data class GetEmployeeListResponse(
    val status: Boolean,
    val message: String,
    val data: List<EmployeeRequest>
)

