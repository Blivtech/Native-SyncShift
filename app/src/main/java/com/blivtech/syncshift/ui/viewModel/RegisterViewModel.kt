package com.blivtech.syncshift.ui.viewModel

import androidx.lifecycle.*
import com.blivtech.syncshift.data.model.ApiState
import com.blivtech.syncshift.data.repository.CommonRepository
import com.blivtech.syncshift.utils.TimeUtils
import com.google.gson.JsonObject
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: CommonRepository) : ViewModel() {

    private val _apiState = MutableLiveData<ApiState>()
    val apiState: LiveData<ApiState> get() = _apiState

    private val _validationError = MutableLiveData<String>()
    val validationError: LiveData<String> get() = _validationError

    fun registerUser(
        name: String,
        phone: String,
        username: String,
        password: String,
        address: String,
        referral: String
    ) {
        if (!validateFields(name, phone, username, password, address)) return

        val postData = JsonObject().apply {
            addProperty("username", username.trim())
            addProperty("password", password.trim())
            addProperty("name", name.trim())
            addProperty("mobile_number", phone.trim())
            addProperty("address", address.trim())

            if (referral.isNotEmpty()) addProperty("referal_id", referral.trim())

            addProperty("contractor_category", "Default")
            addProperty("active_status", "New")
            addProperty("account_type", "User")
            addProperty("mode", "Android-App")
            addProperty("app_version", "1.0.1")
            addProperty("updated_date", TimeUtils.getCurrentDateTime(TimeUtils.FORMAT_1))
            addProperty("active_date", TimeUtils.getCurrentDateTime(TimeUtils.FORMAT_1))
        }

        viewModelScope.launch {
            _apiState.value = ApiState.Loading
            _apiState.value =     repository.registerUser(postData)
        }
    }

    private fun validateFields(
        name: String,
        phone: String,
        username: String,
        password: String,
        address: String
    ): Boolean {
        return when {
            name.isBlank() -> showError("Enter Name")
            phone.isBlank() -> showError("Enter Phone Number")
            username.isBlank() -> showError("Enter Username")
            password.isBlank() -> showError("Enter Password")
            address.isBlank() -> showError("Enter Address")
            else -> true
        }
    }

    private fun showError(message: String): Boolean {
        _validationError.value = message
        return false
    }
}
