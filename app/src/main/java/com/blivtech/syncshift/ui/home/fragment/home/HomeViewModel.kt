package com.blivtech.syncshift.ui.home.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blivtech.syncshift.ui.company.ShiftItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ShiftRepository
) : ViewModel() {


    fun getShiftList(comCode: String): LiveData<List<ShiftItem>> {
        return repository.getShiftWithAttendance(comCode)
    }

    fun insertShiftAttendance(data: ShiftAttendanceEntity) {

        viewModelScope.launch {
            repository.insertShiftAttendance(data)
        }
    }


    fun updateShiftAttendance(data: ShiftAttendanceEntity) {

        viewModelScope.launch {
            repository.updateShiftAttendance(data)
        }
    }


    fun deleteShiftAttendance(data: ShiftAttendanceEntity) {
        viewModelScope.launch {
            repository.deleteShiftAttendance(data)
        }
    }

    fun getAttendanceByDate(date: String): LiveData<List<ShiftAttendanceEntity>> {
        return repository.getAttendanceByDate(date)
    }



}