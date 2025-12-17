package com.blivtech.syncshift.ui.view.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.blivtech.syncshift.R
import com.blivtech.syncshift.data.model.ApiState
import com.blivtech.syncshift.data.repository.CommonRepository
import com.blivtech.syncshift.databinding.ActivityRegisterBinding

import com.blivtech.syncshift.ui.viewModel.RegisterViewModel
import com.blivtech.syncshift.ui.viewModel.RegisterViewModelFactory
import com.blivtech.syncshift.utils.CommonClass

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels {
        RegisterViewModelFactory(CommonRepository())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            if(CommonClass.isInternetAvailable(this)){
                viewModel.registerUser(
                    name = binding.etName.text.toString(),
                    phone = binding.etMobile.text.toString(),
                    username = binding.etUsername.text.toString(),
                    password = binding.etPassword.text.toString(),
                    address = binding.etAddress.text.toString(),
                    referral = binding.etReferral.text.toString()
                )
            }else{
                CommonClass.showToast(this,getString(R.string.str_check_internet))
            }


        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.apiState.observe(this, Observer { state ->
            when (state) {
                is ApiState.Loading -> {
                    binding.btnRegister.isEnabled = false
                }

                is ApiState.Success -> {
                    binding.btnRegister.isEnabled = true
                  //  Toast.makeText(this, "Success: ${state.response}", Toast.LENGTH_LONG).show()
                    val message=state.response?.asJsonObject?.get("message")?.asString?:""
                    CommonClass. showToast(this,message)
                    CommonClass.launchActivity(this, LoginActivity::class.java)
                }

                is ApiState.Error -> {
                    binding.btnRegister.isEnabled = true
                    Toast.makeText(this, "Error: ${state.message}", Toast.LENGTH_LONG).show()
                }
            }
        })

        viewModel.validationError.observe(this, Observer { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        })
    }
}
