package com.blivtech.syncshift.ui.login
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.blivtech.syncshift.R
import com.blivtech.syncshift.data.model.response.UiState
import com.blivtech.syncshift.data.model.request.LoginRequest
import com.blivtech.syncshift.databinding.ActivityLoginBinding
import com.blivtech.syncshift.ui.BaseActivity
import com.blivtech.syncshift.ui.components.ProgressDialog
import com.blivtech.syncshift.ui.home.DashboardActivity
import com.blivtech.syncshift.utils.CommonClass
import com.blivtech.syncshift.utils.SharedPreferencesManager
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding
    private var isPasswordVisible = false
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        applyDisplayCutout(binding.main)

        observeLogin()
        onClickListener()
        setupPasswordToggle()

    }

    private fun observeLogin() {
        val progress= ProgressDialog(this)
        viewModel.loginState.observe(this) {

            when (it) {
                is UiState.Loading -> {
                    progress.show(this.window)
                }

                is UiState.Success -> {
                    progress.dismiss(this.window)
                    it.data?.let { it1 ->
                        SharedPreferencesManager.insertLoginData(this, it1)
                        if(it1.companyDetails.isNotEmpty()){
                            SharedPreferencesManager.setActiveCompanyName(this,it1.companyDetails[0].companyName)
                            SharedPreferencesManager.setActiveCompanyCode(this,it1.companyDetails[0].companyCode)
                            SharedPreferencesManager.setActiveCompanyIndustryName(this,it1.companyDetails[0].companyType)
                        }
                    }
                    CommonClass.launchActivity(this, DashboardActivity::class.java)
                    finish()
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()

                }

                is UiState.Error -> {
                    progress.dismiss(this.window)

                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }



  private  fun onClickListener(){
      binding.btnLogin.setOnClickListener {
          if(CommonClass.isInternetAvailable(this)){
              val request = LoginRequest(
                  userName = binding.etUsername.text.toString().trim(),
                  password = binding.etPassword.text.toString().trim(),
                  mode = "Android-App",
                  appVersion = "1.0.1",
              )

              viewModel.login(request)
          }
          else{
              CommonClass.showToast(this,getString(R.string.str_check_internet))
          }
      }
  }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupPasswordToggle() {

        var isPasswordVisible = false

        binding.etPassword.setOnTouchListener { _, event ->

            if (event.action == android.view.MotionEvent.ACTION_UP) {

                val drawableEnd = 2

                if (event.rawX >= binding.etPassword.right -
                    binding.etPassword.compoundDrawables[drawableEnd].bounds.width()
                ) {

                    isPasswordVisible = !isPasswordVisible

                    if (isPasswordVisible) {
                        binding.etPassword.inputType =
                            android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                        binding.etPassword.setCompoundDrawablesWithIntrinsicBounds(
                            R.drawable.ic_lock, 0, R.drawable.ic_eye_off, 0
                        )
                    } else {
                        binding.etPassword.inputType =
                            android.text.InputType.TYPE_CLASS_TEXT or
                                    android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
                        binding.etPassword.setCompoundDrawablesWithIntrinsicBounds(
                            R.drawable.ic_lock, 0, R.drawable.ic_eye, 0
                        )
                    }

                    binding.etPassword.setSelection(binding.etPassword.text.length)
                    return@setOnTouchListener true
                }
            }

            false
        }


    }



}
