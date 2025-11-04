package com.blivtech.syncshift.data.model.repository

import com.blivtech.syncshift.data.model.local.ApiState
import com.blivtech.syncshift.data.model.network.RetrofitInstance
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import retrofit2.Response

class ApiRepository {

    private val api = RetrofitInstance.apiService

    suspend fun postData(url: String, json: JsonObject): ApiState {
        println("🔗 API Object: $json")
        return safeApiCall { api.postData(url, json) }
    }

    suspend fun getData(url: String): ApiState {
        return safeApiCall { api.getData(url) }
    }


    private suspend fun safeApiCall(apiCall: suspend () -> Response<JsonElement>): ApiState {
        return try {
            val response = apiCall()
            val url = response.raw().request.url.toString()

            println("🔗 API URL: $url")
            if (response.isSuccessful && response.body() != null) {
                ApiState.Success(response.body().toString())
            } else {
                ApiState.Error(response.message() ?: "Unknown error")
            }
        } catch (e: Exception) {
            ApiState.Error(e.localizedMessage ?: "Unexpected error")
        }
    }
}
