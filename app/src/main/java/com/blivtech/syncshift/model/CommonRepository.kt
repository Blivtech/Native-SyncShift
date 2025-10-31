package com.blivtech.syncshift.model

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import retrofit2.Call

class CommonRepository {

    private val apiService = RetrofitInstance.getRetrofitConfigClient().create(ApiService::class.java)

    fun getConfigData(url: String): Call<JsonElement> {
        return apiService.getData(url)
    }

    fun postConfigData(url: String, jsonObject: JsonObject): Call<JsonElement> {
        return apiService.postData(url, jsonObject)
    }

}
