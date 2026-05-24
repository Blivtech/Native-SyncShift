package com.blivtech.syncshift.data.repository

import com.blivtech.syncshift.data.model.request.AdvanceRequest
import com.blivtech.syncshift.data.network.ApiService
import javax.inject.Inject

class AdvanceRepository @Inject constructor(private val  apiService: ApiService) {

    suspend fun saveAdvance(

        request: List<AdvanceRequest>

    ) = apiService.saveAdvance(request)

}