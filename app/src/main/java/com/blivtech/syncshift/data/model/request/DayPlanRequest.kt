package com.blivtech.syncshift.data.model.request

data class DayPlanRequest(
    val activityDate :String,
    val planId: String,
    val shiftName: String,
    val shiftCode: String,
    val companyCode: String,
    val companyName: String,
    val btCode: String,
    val workplanCode: String,
    val workplanName: String,
    val workplanFlag: String,
    val dayType: String,
    val mode: String,
    val remarks: String,
    val submittedAt: String,
    val attendanceDetails: List<EmployeeAttendanceRequest>
)
