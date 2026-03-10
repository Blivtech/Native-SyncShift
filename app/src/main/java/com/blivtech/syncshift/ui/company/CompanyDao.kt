package com.blivtech.syncshift.ui.company

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Insert
import kotlinx.coroutines.flow.Flow

@Dao
interface CompanyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompany(company: CompanyEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompanyList(company: List<CompanyEntity>)

    @Query("SELECT * FROM CompanyEntity")
    fun getAllCompanies(): LiveData<List<CompanyEntity>>

    @Query("SELECT * FROM CompanyEntity WHERE companycode = :companyCode")
    suspend fun getCompany(companyCode: String): CompanyEntity?

    @Delete
    suspend fun deleteCompany(company: CompanyEntity)

    @Query("DELETE FROM CompanyEntity")
    suspend fun clearCompanies()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShifts(list: List<ShiftEntity>)

    @Query("SELECT * FROM ShiftEntity WHERE companyCode = :companyCode")
    fun getShiftsByCompany(companyCode: String): Flow<List<ShiftEntity>>

    @Query("DELETE FROM CompanyEntity")
    suspend fun clearAll()
}