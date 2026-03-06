package com.blivtech.syncshift.ui.company

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CompanyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompany(company: CompanyEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompanyList(company: List<CompanyEntity>)

    @Query("SELECT * FROM company_master")
    fun getAllCompanies(): LiveData<List<CompanyEntity>>

    @Query("SELECT * FROM company_master WHERE companycode = :companyCode")
    suspend fun getCompany(companyCode: String): CompanyEntity?

    @Delete
    suspend fun deleteCompany(company: CompanyEntity)

    @Query("DELETE FROM company_master")
    suspend fun clearCompanies()
}