package com.blivtech.syncshift.view.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.blivtech.syncshift.databinding.ActivityRegisterBinding
import com.blivtech.syncshift.model.ApiState
import com.blivtech.syncshift.model.CommonApiViewModel
import com.blivtech.syncshift.model.CommonRepository
import com.blivtech.syncshift.model.CommonViewModelFactory
import com.google.gson.JsonObject

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val repository = CommonRepository()
        val factory = CommonViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, factory)[CommonApiViewModel::class.java]



        binding.btnRegister.setOnClickListener {
            validateAndRegister()
            viewModel.postData("api/userdetails/save-registration/", postObject())


        }
        viewModel.apiResponse.observe(this) { state ->
            when (state) {
                is ApiState.Loading -> {
                    binding.btnRegister.isEnabled = false
                }

                is ApiState.Success -> {
                    binding.btnRegister.isEnabled = true
                    Toast.makeText(this, "Success: ${state.data}", Toast.LENGTH_LONG).show()
                }

                is ApiState.Error -> {
                    binding.btnRegister.isEnabled = true
                    Toast.makeText(this, "Error: ${state.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun postObject(): JsonObject {
        return JsonObject().apply {
            addProperty("username", "Sj")//design
            addProperty("password", "securepassword123")//design
            addProperty("name", "John Doe")//design
            addProperty("mobile_number", "9876543210")//design
            addProperty("address", "123 Main Street, City")//design
            addProperty("contractor_category", "Electrical")
            addProperty("active_status", "new")
            addProperty("app_version", "1.0.0")
            addProperty("updated_date", "2025-10-29T12:00:00")//empty
            addProperty("active_date", "2025-10-29T10:00:00")//empty
            addProperty("account_type", "Premium")
            addProperty("mode", "Android-App")
            addProperty("referal_id", "REF123")//optonal
        }
    }
    private fun validateAndRegister() {

        val name = binding.etName.text.toString().trim()
        val phone = binding.etMobile.text.toString().trim()
        val address = binding.etAddress.text.toString().trim()
        val contract = binding.etCategory.text.toString().trim()
        val referral = binding.etReferral.text.toString().trim()
        val username = binding.etUsername.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()


        if (name.isEmpty()) {
            binding.etName.error = "Enter Name"
        } else if (phone.isEmpty()) {
            binding.etMobile.error = "Enter Phone Number"
        } else if (address.isEmpty()) {
            binding.etAddress.error = "Enter Address"
        } else if (referral.isEmpty()) {
            binding.etReferral.error = "Enter Referral ID"
        } else if (username.isEmpty()) {
            binding.etUsername.error = "Enter Username"
        } else if (password.isEmpty()) {
            binding.etPassword.error = "Enter Password"
        } else {

            Toast.makeText(this, "✅ Registered Successfully!", Toast.LENGTH_LONG).show()}
    }
}