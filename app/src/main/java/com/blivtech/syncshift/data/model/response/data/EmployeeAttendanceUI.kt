package com.blivtech.syncshift.data.model.response.data

import com.blivtech.syncshift.data.enumi.AttendanceStatus

data class EmployeeAttendanceUI(
    val employeeId: String,
    val name: String,
    val designation: String,
    val phone: String,
    val status: AttendanceStatus
)
