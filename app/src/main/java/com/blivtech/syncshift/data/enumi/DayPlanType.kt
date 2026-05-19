package com.blivtech.syncshift.data.enumi

enum class DayPlanType(
    val code: String,
    val label: String
) {
    WORKING_DAY("WD", "Working Day"),
    HOLIDAY("H", "Holiday"),
    WEEKLY_OFF("WO", "Weekly Off"),
    LEAVE("L", "Leave")
}

enum class DurationType(
    val code: String,
    val label: String
) {
    FULL_DAY("FD", "Full Day"),
    HALF_DAY("HD", "Half Day")
}

