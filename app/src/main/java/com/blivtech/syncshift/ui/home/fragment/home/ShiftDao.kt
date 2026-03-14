package com.blivtech.syncshift.ui.home.fragment.home

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.blivtech.syncshift.ui.company.ShiftEntity
import com.blivtech.syncshift.ui.company.ShiftItem

@Dao
interface ShiftDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShiftMaster(data: ShiftEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShiftAttendance(data: ShiftAttendanceEntity)

    @Update
    suspend fun updateShiftAttendance(data: ShiftAttendanceEntity)

    @Delete
    suspend fun deleteShiftAttendance(data: ShiftAttendanceEntity)

    @Query("SELECT * FROM shift_attendance WHERE date = :date")
    fun getAttendanceByDate(date: String): LiveData<List<ShiftAttendanceEntity>>

    @Query("""
        SELECT 
            sm.shiftName as shiftName,
            sm.shiftCode as shiftCode,
            CASE WHEN sa.shiftCode IS NULL THEN '0' ELSE '1' END as status,
            IFNULL(sa.present,0) as present,
            IFNULL(sa.absent,0) as absent,
            sm.startTime as startTime,
            sm.endTime as endTime,
            '' as shiftImage
        FROM ShiftEntity sm
        LEFT JOIN shift_attendance sa 
        ON sm.shiftCode = sa.shiftCode 
    """)
    fun getShiftWithAttendance(): LiveData<List<ShiftItem>>
}