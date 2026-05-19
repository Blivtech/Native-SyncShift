package com.blivtech.syncshift.ui.attendance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.PrimaryKey
import com.blivtech.syncshift.data.enumi.AttendanceStatus
import com.blivtech.syncshift.data.enumi.DayPlanType
import com.blivtech.syncshift.data.enumi.DurationType
import com.blivtech.syncshift.data.model.local.Dao.CurrentDayPlanDao
import com.blivtech.syncshift.data.model.local.Entity.CurrentDayPlanEntity
import com.blivtech.syncshift.data.model.request.DayPlanRequest
import com.blivtech.syncshift.data.model.request.EmployeeAttendanceRequest
import com.blivtech.syncshift.data.model.response.UiState
import com.blivtech.syncshift.data.model.response.data.EmployeeAttendanceUI
import com.blivtech.syncshift.data.repository.AttendanceRepository
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
@HiltViewModel
class AttendanceViewModel @Inject constructor(
    private val repository: AttendanceRepository,
) : ViewModel() {

    private val selectedDate = MutableStateFlow(getCurrentDate())
    private val _saveState = MutableStateFlow<UiState<Boolean>>(UiState.Loading)

    val dayPlanDetails= repository.getPlanDetails
        .stateIn(viewModelScope, SharingStarted.Lazily, null)
    val saveState: StateFlow<UiState<Boolean>> = _saveState
    val attendanceList: StateFlow<List<EmployeeAttendanceUI>> =
        selectedDate
            .flatMapLatest { date ->
                repository.getAttendance(date)
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )

    fun setDate(date: String) {
        selectedDate.value = date
    }

    fun markAttendance(
       data:  EmployeeAttendanceUI,
        status: AttendanceStatus
    ) {
        viewModelScope.launch {
            repository.markAttendance(
                employeeId = data.employeeId,
                employeeName = data.name,
                date = selectedDate.value,
                status = status
            )
        }
    }

    private fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(Date())
    }



    suspend fun insertAttendanceData(
        data: List<EmployeeAttendanceRequest>,
        ) {
        repository.insertAttendanceData(data)
    }

    fun saveDayPlan(request: DayPlanRequest) {
        viewModelScope.launch {
            _saveState.value = repository.saveDayPlan(request)
            }
        }


    fun insertNewDayPlan(shiftCode:String,shiftName:String,date: String){

        viewModelScope.launch(Dispatchers.IO) {
            val planCode= DayPlanType.WORKING_DAY.code
            val planName= DayPlanType.WORKING_DAY.label
            val duration= DurationType.FULL_DAY.code

            val data=CurrentDayPlanEntity(
                activityDate = date,
                shiftCode = shiftCode,
                shiftName = shiftName,
                planCode = planCode,
                planName = planName,
                duration = duration,
                presentCount = 0,
                absentCount = 0,
                attendanceObject = "[]",
            )
            repository.insertCurrentDayPlan(data)
        }

    }
}

