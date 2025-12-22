package com.blivtech.syncshift.data.model

data class ShiftTiming(
    val id: Int,
    val name: String,
    val time: String,
    var isSelected: Boolean = false
)
