package com.blivtech.syncshift.ui.company

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ShiftEntity",   primaryKeys = ["shiftCode", "companyCode"])
data class ShiftEntity(
    val shiftName: String,
    val companyCode: String,
    val shiftCode: String,
    val startTime: String,
    val endTime: String,
)