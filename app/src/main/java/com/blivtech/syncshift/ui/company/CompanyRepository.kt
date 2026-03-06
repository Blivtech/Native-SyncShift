package com.blivtech.syncshift.ui.company


import javax.inject.Inject

class CompanyRepository @Inject constructor(
    private val companyDao: CompanyDao
) {

    fun getCompanies() = companyDao.getAllCompanies()

}