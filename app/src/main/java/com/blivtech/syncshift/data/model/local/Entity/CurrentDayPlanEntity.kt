package com.blivtech.syncshift.data.model.local.Entity

import androidx.room.Entity
@Entity(tableName = "CurrentDayPlanTable",    primaryKeys = ["activityDate", "shiftCode"])

data class CurrentDayPlanEntity(
    val activityDate: String,
    val shiftCode: String,
    val shiftName: String,
    val planCode: String,
    val planName: String,
    val duration: String,
    val presentCount: Int,
    val absentCount: Int,
    val attendanceObject: String
)