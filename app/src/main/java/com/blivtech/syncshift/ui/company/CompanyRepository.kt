package com.blivtech.syncshift.ui.company


import com.blivtech.syncshift.data.model.ApiResponse
import com.blivtech.syncshift.data.model.response.CompanyDetails
import com.blivtech.syncshift.data.model.response.UiState
import com.blivtech.syncshift.data.network.ApiService
import javax.inject.Inject

class CompanyRepository @Inject constructor(
    private val companyDao: CompanyDao, private val api: ApiService,
) {

    fun getCompanies() = companyDao.getAllCompanies()


    suspend fun saveCompany(
        request: CompanySaveRequestItem
    ): UiState<Boolean> {
        return try {
            val response = api.saveCompany(request)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    if (body.successCode) {
                        body.response?.let { insertCompanyDetails(it) }
                        UiState.Success(true,body.message)
                    } else {
                        UiState.Error(body.message)
                    }
                } else {
                    UiState.Error("Empty response body")
                }
            } else {
                UiState.Error(
                    "HTTP ${response.code()} - ${response.message()}"
                )
            }
        } catch (e: Exception) {
            UiState.Error(e.message ?: "Network Error")
        }
    }


    private suspend fun insertCompanyDetails(data :CompanyDetails){

      val mData=  CompanyEntity(
            companyCode = data.companyCode,
            companyName = data.companyName,
            townCode = data.townCode,
            townName = data.townName,
            companyType = data.companyType,
        )
        companyDao.insertCompany(mData)
        companyDao.insertShifts(data.shiftDetails)


    }
}