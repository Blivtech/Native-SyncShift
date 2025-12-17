package com.blivtech.syncshift.ui.view.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.blivtech.syncshift.R
import com.blivtech.syncshift.databinding.ActivityBottomnavigationBinding
import com.blivtech.syncshift.ui.view.fragment.ContactFragment
import com.blivtech.syncshift.ui.view.fragment.EmployeesFragment
import com.blivtech.syncshift.ui.view.fragment.HomeFragment
import com.blivtech.syncshift.ui.view.fragment.LinkDeviceFragment
import com.blivtech.syncshift.ui.view.fragment.MentionsFragment
import com.blivtech.syncshift.ui.view.fragment.ReportsFragment
import com.blivtech.syncshift.ui.view.fragment.SettingsFragment
import com.blivtech.syncshift.ui.view.fragment.StarredFragment
import com.blivtech.syncshift.ui.viewModel.DemoViewModel


class DashboardActivity: AppCompatActivity() {

    private lateinit var binding: ActivityBottomnavigationBinding

    private lateinit var demoViewModel: DemoViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityBottomnavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)


        replaceFragment(HomeFragment())

        demoViewModel= ViewModelProvider(this)[DemoViewModel::class.java]

        demoViewModel.addition(1,3)


        demoViewModel.addition.observe(this){
            print(it.toString())
        }



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


        binding.navigationView.setNavigationItemSelectedListener { item ->
            if (item.itemId == R.id.nav_mentions) {
                replaceFragment(MentionsFragment())
            } else if (item.itemId == R.id.nav_starred) {
                replaceFragment(StarredFragment())
            } else if (item.itemId == R.id.nav_contact) {
                replaceFragment(ContactFragment())
            } else if (item.itemId == R.id.nav_link_device) {
                replaceFragment(LinkDeviceFragment())
            } else if (item.itemId == R.id.nav_logout) {
                Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show()
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
        binding.imgThreeLine.setOnClickListener {
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
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