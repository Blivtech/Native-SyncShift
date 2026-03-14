package com.blivtech.syncshift.ui.home.fragment.home

import androidx.lifecycle.LiveData
import com.blivtech.syncshift.ui.company.ShiftEntity
import com.blivtech.syncshift.ui.company.ShiftItem
import javax.inject.Inject

class ShiftRepository @Inject constructor(
    private val shiftDao: ShiftDao
) {

    /** Get Shift List With Attendance */
    fun getShiftWithAttendance(): LiveData<List<ShiftItem>> {
        return shiftDao.getShiftWithAttendance()
    }

    /** Insert Shift Master */
    suspend fun insertShiftMaster(data: ShiftEntity) {
        shiftDao.insertShiftMaster(data)
    }

    /** Insert Attendance */
    suspend fun insertShiftAttendance(data: ShiftAttendanceEntity) {
        shiftDao.insertShiftAttendance(data)
    }

    /** Update Attendance */
    suspend fun updateShiftAttendance(data: ShiftAttendanceEntity) {
        shiftDao.updateShiftAttendance(data)
    }

    /** Get Attendance By Date */
    fun getAttendanceByDate(date: String): LiveData<List<ShiftAttendanceEntity>> {
        return shiftDao.getAttendanceByDate(date)
    }

    /** Delete Attendance */
    suspend fun deleteShiftAttendance(data: ShiftAttendanceEntity) {
        shiftDao.deleteShiftAttendance(data)
    }
}