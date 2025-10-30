package com.blivtech.syncshift.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url
import retrofit2.http.Body
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.Headers
import retrofit2.http.QueryMap
import com.google.gson.JsonObject
import com.google.gson.JsonElement

interface ApiService {
    @Headers("Accept:application/json")
    @GET
    fun getData(@Url url: String): Call<JsonElement>

    @POST
    fun postData(@Url url: String, @Body jsonObject: JsonObject): Call<JsonElement>



}