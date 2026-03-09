package com.blivtech.syncshift.ui.company

data class ShiftItem(
    val name: String,
    val code: String,
    val time: String,
    var isSelected: Boolean = false
)