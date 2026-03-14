package com.blivtech.syncshift.ui.home.fragment.home

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shift_attendance")
data class ShiftAttendanceEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val date: String,

    val dayPlan: String,
    val shiftName: String,
    val shiftCode: String,
    val present: String,
    val absent: String,
    val duration: String,
    val presentData: String,

    val absentData: String,

    val remark: String
)