package com.blivtech.syncshift.data.model.repository

import com.blivtech.syncshift.data.model.local.ApiState
import com.blivtech.syncshift.utils.Constants
import com.google.gson.JsonObject

class CommonRepository(
    private val apiRepository: ApiRepository = ApiRepository()
) {
    suspend fun registerUser(json: JsonObject): ApiState {
        return apiRepository.postData(Constants.STR_RID_Register, json)
    }
}
