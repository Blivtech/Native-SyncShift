package com.blivtech.syncshift.data.model.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Headers
import com.google.gson.JsonObject
import com.google.gson.JsonElement
import retrofit2.Response

interface ApiService {
    @Headers("Accept:application/json")
    @GET
  suspend  fun getData(@Url url: String): Response<JsonElement>

    @POST
   suspend fun postData(@Url url: String, @Body jsonObject: JsonObject): Response<JsonElement>



}