package com.blivtech.syncshift.ui.viewModel

import androidx.lifecycle.*
import com.blivtech.syncshift.data.model.ApiState
import com.blivtech.syncshift.data.repository.ApiRepository
import com.google.gson.JsonObject
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: ApiRepository) : ViewModel() {

    private val _loginState = MutableLiveData<ApiState>()
    val loginState: LiveData<ApiState> get() = _loginState

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _loginState.value = ApiState.Loading

            val json = JsonObject().apply {
                addProperty("username", username)
                addProperty("password", password)
            }

            val response = repository.postData("login", json)


            _loginState.value = response
        }
    }
}
