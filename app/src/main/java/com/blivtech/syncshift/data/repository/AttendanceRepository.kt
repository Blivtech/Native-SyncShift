package com.blivtech.syncshift.data.repository

import com.blivtech.syncshift.data.enumi.AttendanceStatus
import com.blivtech.syncshift.data.model.local.Dao.AttendanceDao
import com.blivtech.syncshift.data.model.local.Entity.AttendanceEntity
import com.blivtech.syncshift.data.model.request.AttendanceRequest
import com.blivtech.syncshift.data.model.request.DayPlanRequest
import com.blivtech.syncshift.data.model.response.UiState
import com.blivtech.syncshift.data.model.response.data.EmployeeAttendanceUI
import com.blivtech.syncshift.data.network.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AttendanceRepository @Inject constructor(
    private val attendanceDao: AttendanceDao,
    private val api: ApiService

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


    suspend fun getAttendanceList(): List<AttendanceRequest> {
        return attendanceDao.getAttendanceRequestList()
    }



    fun saveDayPlan(request: DayPlanRequest): Flow<UiState<Unit>> = flow {

        val finalrequest=request.copy(attendance = getAttendanceList())
        try {
            val response = api.saveDayPlan(finalrequest)

            if (response.isSuccessful) {

            } else {
                emit(
                    UiState.Error(
                        response.message().ifEmpty { "Failed to save attendance" }
                    )
                )
            }
        } catch (e: Exception) {
            emit(UiState.Error(e.localizedMessage ?: "Something went wrong"))
        }
    }

}

