package com.blivtech.syncshift.data.model.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EmployeeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmployees(list: List<EmployeeEntity>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmployee(data: EmployeeEntity)
    @Query("SELECT * FROM employee ORDER BY employee_name ASC")
    fun getEmployees(): Flow<List<EmployeeEntity>>

    @Query("DELETE FROM employee")
    suspend fun clearEmployees()
}
