package com.blivtech.syncshift.data.model.request

data class EmployeeAttendanceRequest(
    val employeeName: String,
    val employeeCode: String,
    val status: String
)