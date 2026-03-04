package com.blivtech.syncshift.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blivtech.syncshift.data.model.request.LoginRequest
import com.blivtech.syncshift.data.model.response.UiState
import com.blivtech.syncshift.data.model.response.LoginResponse
import com.blivtech.syncshift.data.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository
) : ViewModel() {

    private val _loginState = MutableLiveData<UiState<LoginResponse>>()
    val loginState: LiveData<UiState<LoginResponse>> = _loginState

    fun login(request: LoginRequest) {

        viewModelScope.launch {

            _loginState.value = UiState.Loading

            val result = repository.login(request)

            _loginState.value = result
        }
    }
}
