package com.blivtech.syncshift.data.model.response

sealed class UiState<out T> {
    data class Success<T>(val data: T,val message :String) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
    object Loading : UiState<Nothing>()
}
