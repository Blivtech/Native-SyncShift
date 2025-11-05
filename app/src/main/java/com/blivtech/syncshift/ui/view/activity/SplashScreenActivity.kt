package com.blivtech.syncshift.ui.view.activity

import android.os.Bundle
import android.annotation.SuppressLint
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import androidx.appcompat.app.AppCompatActivity
import com.blivtech.syncshift.databinding.ActivitySplashBinding
import com.blivtech.syncshift.ui.viewModel.SplashViewModel
import com.blivtech.syncshift.utils.CommonClass


/*
Developed By   : Saravana Kumar P
*/




@Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var splashViewModel: SplashViewModel
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        splashViewModel = ViewModelProvider(this)[SplashViewModel::class.java]

        splashViewModel.init(this)

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