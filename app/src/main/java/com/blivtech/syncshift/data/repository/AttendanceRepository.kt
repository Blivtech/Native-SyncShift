package com.blivtech.syncshift.data.repository

import androidx.lifecycle.LiveData
import com.blivtech.syncshift.data.enumi.AttendanceStatus
import com.blivtech.syncshift.data.model.local.Dao.AttendanceDao
import com.blivtech.syncshift.data.model.local.Dao.CurrentDayPlanDao
import com.blivtech.syncshift.data.model.local.Entity.AttendanceEntity
import com.blivtech.syncshift.data.model.local.Entity.CurrentDayPlanEntity
import com.blivtech.syncshift.data.model.local.Entity.EmployeeEntity
import com.blivtech.syncshift.data.model.request.AttendanceRequest
import com.blivtech.syncshift.data.model.request.DayPlanRequest
import com.blivtech.syncshift.data.model.request.EmployeeAttendanceRequest
import com.blivtech.syncshift.data.model.response.UiState
import com.blivtech.syncshift.data.model.response.data.EmployeeAttendanceUI
import com.blivtech.syncshift.data.network.ApiService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AttendanceRepository @Inject constructor(

    private val attendanceDao: AttendanceDao,
    private val currentDayPlanDao: CurrentDayPlanDao,
    private val api: ApiService

) {
    val getPlanDetails = currentDayPlanDao.getPlanDetails()

    fun getAttendance(date: String): Flow<List<EmployeeAttendanceUI>> {
        return attendanceDao.getAttendanceForDate(date)
    }

    suspend fun markAttendance(
        employeeId: String,
        employeeName: String,
        date: String,
        status: AttendanceStatus
    ) {
        attendanceDao.insertAttendance(
            AttendanceEntity(
                employee_id = employeeId,
                employeeName = employeeName,
                attendance_date = date,
                status = status
            )
        )
    }


    suspend fun getAttendanceList(): List<AttendanceRequest> {
        return attendanceDao.getAttendanceRequestList()
    }


    suspend fun insertCurrentDayPlan(data: CurrentDayPlanEntity)  {
        currentDayPlanDao.deleteAll()
        return currentDayPlanDao.insertAttendance(data)
    }
    suspend fun insertAttendanceData(
        data: List<EmployeeAttendanceRequest>,
    ) {
        val gson = Gson()
        val attendanceJson = gson.toJson(data)


        val presentCount = data.count { it.status == "PRESENT" }
        val absentCount = data.count { it.status == "ABSENT" }


        currentDayPlanDao.updateAttendance(
            presentCount = presentCount,
            absentCount = absentCount,
            attendanceJson = attendanceJson
        )
    }

    suspend fun saveDayPlan(request: DayPlanRequest): UiState<Boolean> {

        val attendanceList = currentDayPlanDao.getAttendance().toAttendanceList()
       val updatedRequest = request.copy(attendanceDetails = attendanceList)

        return try {
            val response = api.saveDayPlan(updatedRequest)

            if (response.isSuccessful && response.body() != null) {
                UiState.Success(true,"Success")

            } else {
                UiState.Error("Error: ${response.code()} ${response.message()}")
            }

        } catch (e: Exception) {
            UiState.Error(e.message ?: "Unknown error")
        }
    }
    private fun String?.toAttendanceList(): List<EmployeeAttendanceRequest> {
        if (this.isNullOrEmpty()) return emptyList()
        val type = object : TypeToken<List<EmployeeAttendanceRequest>>() {}.type
        return Gson().fromJson(this, type)
    }

}

