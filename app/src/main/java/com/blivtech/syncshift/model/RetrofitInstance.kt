package com.blivtech.syncshift.model

import com.blivtech.syncshift.utils.ApiConstants
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance {
    private var retrofit: Retrofit? = null

    fun getRetrofitConfigClient(): Retrofit {
        return getRetrofitClient()
    }

    private fun getRetrofitClient(): Retrofit {
        val gson = GsonBuilder().setLenient().create()
        retrofit =
            Retrofit.Builder().baseUrl(ApiConstants.BASE_URL).client(HttpClient.getUnsafeOkHttpClient().build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()

        return retrofit as Retrofit
    }
}