package com.blivtech.syncshift.ui.home

import android.content.pm.ActivityInfo
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.res.ResourcesCompat.getColor
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.blivtech.syncshift.R
import com.blivtech.syncshift.databinding.ActivityBottomnavigationBinding
import com.blivtech.syncshift.ui.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class DashboardActivity : BaseActivity() {

    private lateinit var binding: ActivityBottomnavigationBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBottomnavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        applyDisplayCutout(binding.main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        setupCustomBottomNav()
        observeNavigation()
    }

    private fun setupCustomBottomNav() {
        binding.tabHome.setOnClickListener {
            navigateTo(R.id.homeFragment)
        }

        binding.tabEmployees.setOnClickListener {
            navigateTo(R.id.employeesFragment)
        }

        binding.tabReports.setOnClickListener {
            navigateTo(R.id.reportFragment)
        }
        binding.tabMenu.setOnClickListener {
            navigateTo(R.id.menuFragment)
        }

        // Default tab
        highlightTab(1)
    }

    private fun observeNavigation() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment -> highlightTab(1)
                R.id.employeesFragment -> highlightTab(2)
                R.id.reportFragment -> highlightTab(3)
                R.id.menuFragment -> highlightTab(4)
            }
        }
    }

    private fun navigateTo(destinationId: Int) {
        if (navController.currentDestination?.id != destinationId) {
            navController.navigate(destinationId)
        }
    }

    private fun highlightTab(selected: Int) {


        setTabState(binding.iconHome, binding.textHome, false)
        setTabState(binding.iconEmployees, binding.textEmployees, false)
        setTabState(binding.iconReports, binding.textReports, false)
        setTabState(binding.iconMenu, binding.textMenu, false)

        when (selected) {
            1 -> {
                setTabState(binding.iconHome, binding.textHome, true)
                animateShow(binding.textHome)
            }
            2 -> {
                setTabState(binding.iconEmployees, binding.textEmployees, true)
                animateShow(binding.textEmployees)
            }
            3 -> {
                setTabState(binding.iconReports, binding.textReports, true)
                animateShow(binding.textReports)
            }
           4 -> {
                setTabState(binding.iconMenu, binding.textMenu, true)
                animateShow(binding.iconMenu)
            }
        }
    }

    // 🔥 ICON + TEXT COLOR HANDLER
    private fun setTabState(icon: ImageView, text: TextView, selected: Boolean) {
        val color = if (selected) {
            getColor(R.color.button_color)
        } else {
            getColor(R.color.navication_color)
        }

        icon.setColorFilter(color, PorterDuff.Mode.SRC_IN)
        text.setTextColor(color)
    }

    private fun animateShow(view: View) {
        view.apply {
            visibility = View.VISIBLE
            alpha = 0f
            scaleX = 0.6f
            scaleY = 0.6f
            translationY = 16f

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
        view.animate()
            .alpha(0f)
            .scaleX(0.6f)
            .scaleY(0.6f)
            .setDuration(180)
            .withEndAction { view.visibility = View.GONE }
            .start()
    }
}



