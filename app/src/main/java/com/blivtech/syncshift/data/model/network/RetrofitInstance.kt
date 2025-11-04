package com.blivtech.syncshift.data.model.network


import com.blivtech.syncshift.utils.Constants
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private var retrofit: Retrofit? = null

    val apiService: ApiService by lazy {
        getRetrofitClient().create(ApiService::class.java)
    }

    private fun getRetrofitClient(): Retrofit {
        val gson = GsonBuilder().setLenient().create()
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(HttpClient.getUnsafeOkHttpClient().build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }
        return retrofit!!
    }
}
