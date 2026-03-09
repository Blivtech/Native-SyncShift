package com.blivtech.syncshift.data.model.local

import androidx.room.TypeConverter
import com.blivtech.syncshift.data.enumi.AttendanceStatus

class AttendanceStatusConverter {

    @TypeConverter
    fun fromStatus(status: AttendanceStatus?): String {
        return status?.name ?: AttendanceStatus.NOT_IN_YET.name
    }

    @TypeConverter
    fun toStatus(value: String?): AttendanceStatus {
        return AttendanceStatus.valueOf(
            value ?: AttendanceStatus.NOT_IN_YET.name
        )
    }
}

