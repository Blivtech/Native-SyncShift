package com.blivtech.syncshift.model

import com.blivtech.syncshift.model.RetrofitInstance
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Response
import retrofit2.adapter.rxjava2.Result

class CommonRepository {

    private val apiService = RetrofitInstance.getRetrofitConfigClient().create(ApiService::class.java)

    fun getConfigData(url: String): Call<JsonElement> {
        return apiService.getData(url)
    }

    fun postConfigData(url: String, jsonObject: JsonObject): Call<JsonElement> {
        return apiService.postData(url, jsonObject)
    }

}
