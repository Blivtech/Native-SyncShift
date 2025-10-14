package com.blivtech.syncshift.view.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.blivtech.syncshift.R
import com.blivtech.syncshift.databinding.ActivityBottomnavigationBinding
import com.blivtech.syncshift.view.fragment.EmployeesFragment
import com.blivtech.syncshift.view.fragment.HomeFragment
import com.blivtech.syncshift.view.fragment.ReportsFragment
import com.blivtech.syncshift.view.fragment.SettingsFragment


class DashboardActivity:AppCompatActivity() {

    private lateinit var binding: ActivityBottomnavigationBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityBottomnavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replacefragment(HomeFragment())

        binding.bottomnavigation.setOnItemSelectedListener { item ->

            if (item.itemId == R.id.mnu_home) {
                replacefragment(HomeFragment())
                true
            } else if (item.itemId == R.id.mnu_employees) {
                replacefragment(EmployeesFragment())
                true
            } else if (item.itemId == R.id.mnu_reports) {
                replacefragment(ReportsFragment())
                true
            } else if (item.itemId == R.id.mnu_settings) {
                replacefragment(SettingsFragment())
                true
            }else{
                false
        }
        }

    }
    private fun replacefragment(fragment: Fragment){

        val fragmentManager=supportFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.framelayout,fragment)
        fragmentTransaction.commit()

    }

}