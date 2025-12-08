package com.blivtech.syncshift.ui.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.blivtech.syncshift.R
import com.blivtech.syncshift.databinding.ActivityBottomnavigationBinding
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBottomnavigationBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBottomnavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        setupCustomBottomNav()
    }

    private fun setupCustomBottomNav() {
        binding.tabHome.setOnClickListener {
            navController.navigate(R.id.homeFragment)
            highlightTab(1)
        }

        binding.tabEmployees.setOnClickListener {
            navController.navigate(R.id.employeesFragment)
            highlightTab(2)
        }

        binding.tabReports.setOnClickListener {
            navController.navigate(R.id.reportFragment)
            highlightTab(3)
        }

        highlightTab(1)
    }


    private fun highlightTab(selected: Int) {

        // Hide all text first
        val allTexts = listOf(
            binding.textHome,
            binding.textEmployees,
            binding.textReports
        )

        allTexts.forEach { txt ->
            if (txt.visibility == View.VISIBLE) animateHide(txt)
        }

        // Reset all icons to UNSELECTED
        binding.iconHome.setImageResource(R.drawable.svg_home_unselected)
        binding.iconEmployees.setImageResource(R.drawable.svg_employees_unselected)
        binding.iconReports.setImageResource(R.drawable.svg_report_unselected)

        // Apply selected changes
        when (selected) {
            1 -> {
                binding.iconHome.setImageResource(R.drawable.svg_home_selected)
                animateShow(binding.textHome)
            }
            2 -> {
                binding.iconEmployees.setImageResource(R.drawable.svg_employees_selected)
                animateShow(binding.textEmployees)
            }
            3 -> {
                binding.iconReports.setImageResource(R.drawable.svg_report_selected)
                animateShow(binding.textReports)
            }
        }
    }

    private fun animateShow(view: View) {
        view.apply {
            visibility = View.VISIBLE
            alpha = 0f
            scaleX = 0.6f
            scaleY = 0.6f
            translationY = 20f

            animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .translationY(0f)
                .setDuration(180)
                .start()
        }
    }

    private fun animateHide(view: View) {
        view.apply {
            animate()
                .alpha(0f)
                .scaleX(0.6f)
                .scaleY(0.6f)
                .setDuration(180)
                .withEndAction {
                    visibility = View.GONE
                }
                .start()
        }
    }


}

