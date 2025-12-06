package com.blivtech.syncshift.ui.splash

import android.os.Bundle
import android.annotation.SuppressLint
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import androidx.appcompat.app.AppCompatActivity
import com.blivtech.syncshift.databinding.ActivitySplashBinding
import com.blivtech.syncshift.ui.home.DashboardActivity
import com.blivtech.syncshift.ui.login.LoginActivity
import com.blivtech.syncshift.utils.CommonClass
import dagger.hilt.android.AndroidEntryPoint


/*
Developed By   : Saravana Kumar P
*/



@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var splashViewModel: SplashViewModel
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        splashViewModel = ViewModelProvider(this)[SplashViewModel::class.java]

        splashViewModel.startNavigation()

        navigation()
    }

    private fun navigation() {
        splashViewModel.navigateTo.observe(this) { destination ->

            when (destination) {

                SplashViewModel.Destination.LOGIN -> {
                    CommonClass.launchActivity(this, LoginActivity::class.java)
                        finish()
                    }

                SplashViewModel.Destination.DASHBOARD -> {
                    CommonClass.launchActivity(this, DashboardActivity::class.java)
                    finish()
                }

                else -> {
                    CommonClass.launchActivity(this, LoginActivity::class.java)
                    finish()
                }
            }
        }
    }
}