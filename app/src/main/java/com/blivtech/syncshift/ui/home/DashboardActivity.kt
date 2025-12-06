package com.blivtech.syncshift.ui.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.blivtech.syncshift.R
import com.blivtech.syncshift.databinding.ActivityBottomnavigationBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DashboardActivity: AppCompatActivity() {

    private lateinit var binding: ActivityBottomnavigationBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityBottomnavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)


        replaceFragment(HomeFragment())




        binding.bottomnavigation.setOnItemSelectedListener { item ->
            if (item.itemId == R.id.mnu_home) {
                replaceFragment(HomeFragment())
            } else if (item.itemId == R.id.mnu_employees) {
                replaceFragment(EmployeesFragment())
            } else if (item.itemId == R.id.mnu_reports) {
                replaceFragment(ReportsFragment())
            } else if (item.itemId == R.id.mnu_settings) {
                replaceFragment(SettingsFragment())
            }
            true
        }

        binding.drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {

                val scale = 1 - (slideOffset * 0.1f)
                binding.mainContent.apply {
                    scaleX = scale
                    scaleY = scale
                    translationX = drawerView.width * slideOffset * 0.9f
                }
            }

            override fun onDrawerOpened(drawerView: View) {}
            override fun onDrawerClosed(drawerView: View) {}
            override fun onDrawerStateChanged(newState: Int) {}
        })
    }


private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
            .commit()
    }


}