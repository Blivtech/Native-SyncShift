package com.blivtech.syncshift.data.model.local.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.blivtech.syncshift.data.model.local.Entity.AttendanceEntity
import com.blivtech.syncshift.data.model.response.data.EmployeeAttendanceUI
import kotlinx.coroutines.flow.Flow
@Dao
interface AttendanceDao {

    @Query("""
        SELECT 
            e.employee_id AS employeeId,
            e.employee_name AS name,
            e.designation AS designation,
            e.phone AS phone,
            CASE 
                WHEN a.status IS NULL THEN 'NOT_IN_YET'
                ELSE a.status
            END AS status
        FROM employee e
        LEFT JOIN attendance a
            ON e.employee_id = a.employee_id
            AND a.attendance_date = :date
    """)
    fun getAttendanceForDate(date: String): Flow<List<EmployeeAttendanceUI>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttendance(attendance: AttendanceEntity)
}

