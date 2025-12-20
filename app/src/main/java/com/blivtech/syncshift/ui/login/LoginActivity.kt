package com.blivtech.syncshift.ui.login
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.blivtech.syncshift.R
import com.blivtech.syncshift.data.model.Resource
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
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        applyDisplayCutout(binding.main)

        observeLogin()
        onClickListener()

    }

    private fun observeLogin() {
        val progress= ProgressDialog(this)
        viewModel.loginState.observe(this) {

            when (it) {
                is Resource.Loading -> {
                    progress.show(this.window)
                }

                is Resource.Success -> {
                    progress.dismiss(this.window)
                    if(it.data?.success == true){
                        it.data.data?.let { it1 ->
                            SharedPreferencesManager.insertLoginData(this, it1)
                        }
                        CommonClass.launchActivity(this,DashboardActivity::class.java)
                        finish()
                    }
                    Toast.makeText(this, it.data?.message, Toast.LENGTH_SHORT).show()
                }

                is Resource.Error -> {
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
                  username = binding.etUsername.text.toString().trim(),
                  password = binding.etPassword.text.toString().trim(),
                  mode = "Android-App",
                  app_version = "1.0.1",
                  updated_date = ""   // Auto added in UseCase
              )

              viewModel.login(request)
          }
          else{
              CommonClass.showToast(this,getString(R.string.str_check_internet))
          }
      }
  }


}
