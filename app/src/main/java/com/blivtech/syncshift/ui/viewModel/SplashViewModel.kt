package com.blivtech.syncshift.ui.viewModel

import android.content.Context
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.MutableLiveData
import com.blivtech.syncshift.utils.SharedPreferencesManager

/*
Developed By   : SaravanaKumar P
*/

class SplashViewModel : ViewModel() {
    private var delay: Long = 3500

    private val _navigateTo = MutableLiveData<Destination>()
    val navigateTo: LiveData<Destination> get() = _navigateTo

    enum class Destination {
        LOGIN,
        DASHBOARD
    }


    fun init(context: Context) {
        viewModelScope.launch {
            if (SharedPreferencesManager.getLogInStatus(context)) {
                _navigateTo.postValue(Destination.DASHBOARD)
            } else {
                _navigateTo.postValue(Destination.LOGIN)
            }
            delay(delay)
        }
    }
}