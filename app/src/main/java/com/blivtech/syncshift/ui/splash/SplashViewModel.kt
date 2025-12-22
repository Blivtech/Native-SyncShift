package com.blivtech.syncshift.ui.splash
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.blivtech.syncshift.utils.SharedPreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
Developed By   : SaravanaKumar P
*/

@HiltViewModel
class SplashViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    private val splashDelay = 2500L  // 2.5 seconds

    private val _navigateTo = MutableLiveData<Destination>()
    val navigateTo: LiveData<Destination> get() = _navigateTo

    enum class Destination {
        LOGIN,
        DASHBOARD
    }

    fun startNavigation() {
        viewModelScope.launch {

            delay(splashDelay)

            val isLoggedIn = SharedPreferencesManager.getLogInStatus(
                getApplication<Application>().applicationContext
            )

            _navigateTo.value =
                if (isLoggedIn) Destination.DASHBOARD
                else Destination.LOGIN
        }
    }
}
