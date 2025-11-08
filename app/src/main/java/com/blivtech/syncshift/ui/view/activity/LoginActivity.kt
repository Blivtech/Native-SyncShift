package com.blivtech.syncshift.ui.view.activity


import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.blivtech.syncshift.R
import com.blivtech.syncshift.data.model.ApiState
import com.blivtech.syncshift.data.repository.ApiRepository
import com.blivtech.syncshift.databinding.ActivityLoginBinding
import com.blivtech.syncshift.ui.viewModel.LoginViewModel
import com.blivtech.syncshift.ui.viewModel.LoginViewModelFactory
import com.blivtech.syncshift.utils.CommonClass
import com.blivtech.syncshift.utils.SharedPreferencesManager


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels {
        LoginViewModelFactory(ApiRepository())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listener()
        observeLogin()
    }

    private fun listener() {

        binding.tvSignUp.setOnClickListener {
            CommonClass.launchActivity(this, RegisterActivity::class.java)

        }

        binding.btnLogin.setOnClickListener {
            if(CommonClass.isInternetAvailable(this)){
                val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (username.isEmpty()) {
                CommonClass.showToast(this, "Enter username")
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                CommonClass.showToast(this, "Enter password")
                return@setOnClickListener
            }

            viewModel.login(username, password)
            }else{
                CommonClass.showToast(this,getString(R.string.str_check_internet))
            }
        }
    }

    private fun observeLogin() {
        viewModel.loginState.observe(this, Observer { state ->
            when (state) {
                is ApiState.Loading -> {
                    binding.btnLogin.isEnabled = false
                }
                is ApiState.Success -> {
                    binding.btnLogin.isEnabled = true
                    val json = state.response?.asJsonObject
                    val success = json?.get("success")?.asBoolean ?: false
                    val message = json?.get("message")?.asString ?: "Login response"
                    CommonClass.showToast(this, message)
                    if (success) {
                        SharedPreferencesManager.setLoginStatus(this, true)
                        CommonClass.launchActivity(this, DashboardActivity::class.java)
                        finish()
                    } else {
                        CommonClass.showToast(this, message)
                    }
                }
                is ApiState.Error -> {

                }
            }
        })
    }
}
