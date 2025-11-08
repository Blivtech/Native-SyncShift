package com.blivtech.syncshift.data.repository

import com.blivtech.syncshift.data.model.ApiState
import com.blivtech.syncshift.utils.Constants
import com.google.gson.JsonObject

class CommonRepository(
    private val apiRepository: ApiRepository = ApiRepository()
) {
    suspend fun registerUser(json: JsonObject): ApiState {
        return apiRepository.postData(Constants.STR_RID_Register, json)
    }
}
