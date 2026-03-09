package com.blivtech.syncshift.ui.company

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.blivtech.syncshift.R
import com.blivtech.syncshift.databinding.ActivityCompanyBinding
import com.blivtech.syncshift.ui.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CompanyActivity :BaseActivity() {

        private lateinit var binding: ActivityCompanyBinding
        private lateinit var navController: NavController

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityCompanyBinding.inflate(layoutInflater)
            setContentView(binding.root)

            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            navController = navHostFragment.navController
        }

}