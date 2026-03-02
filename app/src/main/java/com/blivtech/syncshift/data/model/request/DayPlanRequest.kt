package com.blivtech.syncshift.data.model.request

data class DayPlanRequest(
    val planid: String,
    val btcode: String,
    val activityDate: String,
    val shiftCode: String,
    val shiftName: String,
    val workPlan: String,
    val workplanCode: String,
    val durationType: String,
    val durationCode: String,
    val Remark: String,
    val AppMode: String,
    val AppVersion: String,
    val DeviceName: String,
    val created_by: String,
    val attendance: List<AttendanceRequest>
)
