package com.blivtech.syncshift.data.model.local.Entity

import androidx.room.Entity
import androidx.room.Index
import com.blivtech.syncshift.data.enumi.AttendanceStatus

@Entity(
    tableName = "attendance",
    primaryKeys = ["employee_id", "attendance_date"],
    indices = [Index("employee_id"), Index("attendance_date")]
)
data class AttendanceEntity(
    val employee_id: String,
    val attendance_date: String, // yyyy-MM-dd
    val status: AttendanceStatus
)
