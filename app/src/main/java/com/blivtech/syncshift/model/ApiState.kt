package com.blivtech.syncshift.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

sealed class ApiState {
    object Loading : ApiState()
    data class Success(val data: JsonElement?) : ApiState()
    data class Error(val message: String) : ApiState()
}

class CommonApiViewModel(private val repository: CommonRepository) : ViewModel() {

    private val _apiResponse = MutableLiveData<ApiState>()
    val apiResponse: LiveData<ApiState> get() = _apiResponse

    fun getData(url: String) {
        _apiResponse.value = ApiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            repository.getConfigData(url).enqueue(object : Callback<JsonElement> {
                override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                    if (response.isSuccessful) {
                        _apiResponse.postValue(ApiState.Success(response.body()))
                    } else {
                        _apiResponse.postValue(ApiState.Error(response.message()))
                    }
                }

                override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                    _apiResponse.postValue(ApiState.Error(t.localizedMessage ?: "Unknown error"))
                }
            })
        }
    }

    fun postData(url: String, body: JsonObject) {
        _apiResponse.value = ApiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            repository.postConfigData(url, body).enqueue(object : Callback<JsonElement> {
                override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                    if (response.isSuccessful) {
                        _apiResponse.postValue(ApiState.Success(response.body()))
                    } else {
                        _apiResponse.postValue(ApiState.Error(response.message()))
                    }
                }

                override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                    _apiResponse.postValue(ApiState.Error(t.localizedMessage ?: "Unknown error"))
                }
            })
        }
    }
}
