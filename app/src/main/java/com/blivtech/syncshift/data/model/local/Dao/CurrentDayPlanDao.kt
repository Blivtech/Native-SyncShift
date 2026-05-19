package com.blivtech.syncshift.data.model.local.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.blivtech.syncshift.data.model.local.Entity.CurrentDayPlanEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrentDayPlanDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttendance(data: CurrentDayPlanEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(data: List<CurrentDayPlanEntity>)

    @Query("SELECT * FROM CurrentDayPlanTable LIMIT 1")
    fun getPlanDetails(): Flow<CurrentDayPlanEntity>

    @Query("SELECT * FROM CurrentDayPlanTable WHERE activityDate = :date")
    suspend fun getAttendanceByDate(date: String): List<CurrentDayPlanEntity>

    @Query("DELETE FROM CurrentDayPlanTable")
    suspend fun clearAll()

    @Query("DELETE FROM CurrentDayPlanTable")
    suspend fun deleteAll()

    @Query("""
    UPDATE CurrentDayPlanTable 
    SET presentCount = :presentCount,
        absentCount = :absentCount,
        attendanceObject = :attendanceJson
""")
    suspend fun updateAttendance(
        presentCount: Int,
        absentCount: Int,
        attendanceJson: String
    )

    @Query("SELECT attendanceObject FROM CurrentDayPlanTable")
    suspend fun getAttendance():String





}