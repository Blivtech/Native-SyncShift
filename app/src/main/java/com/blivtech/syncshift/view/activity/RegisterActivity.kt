package com.blivtech.syncshift.view.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.blivtech.syncshift.databinding.ActivityRegisterBinding
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

        viewModel.postData("", JsonObject())



        viewModel.apiResponse.observe(this){


        }

        binding.btnRegister.setOnClickListener {
            validateAndRegister()
        }
    }

    private fun validateAndRegister() {


        val name = binding.etName.text.toString().trim()
        val phone = binding.etPhone.text.toString().trim()
        val address = binding.etAddress.text.toString().trim()
        val contract = binding.etContract.text.toString().trim()
        val referral = binding.etReferral.text.toString().trim()
        val username = binding.etUsername.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()


        if (name.isEmpty()) {
            binding.etName.error = "Enter Name"
        } else if (phone.isEmpty()) {
            binding.etPhone.error = "Enter Phone Number"
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