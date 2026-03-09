package com.blivtech.syncshift.data.model.request

data class AttendanceRequest(
    val name: String,
    val code: String,
    val status: Int   // 1 = Present, 0 = Absent
)
