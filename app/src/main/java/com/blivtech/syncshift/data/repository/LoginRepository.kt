package com.blivtech.syncshift.data.repository

import com.blivtech.syncshift.data.model.request.LoginRequest
import com.blivtech.syncshift.data.model.response.CompanyDetails
import com.blivtech.syncshift.data.model.response.LoginResponse
import com.blivtech.syncshift.data.model.response.UiState
import com.blivtech.syncshift.data.network.ApiService
import com.blivtech.syncshift.ui.company.CompanyDao
import com.blivtech.syncshift.ui.company.toListCompanyEntity
import javax.inject.Inject

class LoginRepository @Inject constructor(private val api: ApiService,private val companyDao: CompanyDao) {

    suspend fun login(
        request: LoginRequest
    ): UiState<LoginResponse> {

        return try {
            val response = api.login(request)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    if (body.successCode) {
                        insertCompanyDetails(body.response!!.companyDetails)
                        UiState.Success(body.response!!,body.message)
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


    private suspend fun insertCompanyDetails(data :List<CompanyDetails>){
        companyDao.insertCompanyList(data.toListCompanyEntity())
        data.forEach {
            companyDao.insertShifts(it.shiftDetails)

        }
    }


}
