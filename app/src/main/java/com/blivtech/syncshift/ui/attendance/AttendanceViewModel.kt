package com.blivtech.syncshift.ui.attendance

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blivtech.syncshift.data.enumi.AttendanceStatus
import com.blivtech.syncshift.data.model.response.data.EmployeeAttendanceUI
import com.blivtech.syncshift.data.repository.AttendanceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale
import javax.inject.Inject
@HiltViewModel
class AttendanceViewModel @Inject constructor(
    private val repository: AttendanceRepository
) : ViewModel() {

    // ✅ Default = today (yyyy-MM-dd)
    private val selectedDate = MutableStateFlow(getCurrentDate())

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
        employeeId: String,
        status: AttendanceStatus
    ) {
        viewModelScope.launch {
            repository.markAttendance(
                employeeId = employeeId,
                date = selectedDate.value,
                status = status
            )
        }
    }

    private fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(Date())
    }
}

