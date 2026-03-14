package com.blivtech.syncshift.ui.company

data class ShiftItem(
    val shiftName: String,
    val shiftCode: String,
    val status: String,
    val present: Int,
    val absent: Int,
    val startTime: String,
    val endTime: String,
    val shiftImage: String
)