package com.blivtech.syncshift.data.model.response

data class ShiftTiming(
    var code: String,
    var name: String,
    val time: String,
    var isSelected: Boolean = false
)
