package com.blivtech.syncshift.data.repository

import com.blivtech.syncshift.data.enumi.AttendanceStatus
import com.blivtech.syncshift.data.model.local.Dao.AttendanceDao
import com.blivtech.syncshift.data.model.local.Entity.AttendanceEntity
import com.blivtech.syncshift.data.model.response.data.EmployeeAttendanceUI
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AttendanceRepository @Inject constructor(
    private val attendanceDao: AttendanceDao
) {

    fun getAttendance(date: String): Flow<List<EmployeeAttendanceUI>> {
        return attendanceDao.getAttendanceForDate(date)
    }

    suspend fun markAttendance(
        employeeId: String,
        date: String,
        status: AttendanceStatus
    ) {
        attendanceDao.insertAttendance(
            AttendanceEntity(
                employee_id = employeeId,
                attendance_date = date,
                status = status
            )
        )
    }
}

