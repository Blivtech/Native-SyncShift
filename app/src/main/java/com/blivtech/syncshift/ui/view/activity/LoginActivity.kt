package com.blivtech.syncshift.ui.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.blivtech.syncshift.databinding.ActivityLoginBinding
import com.blivtech.syncshift.utils.CommonClass

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvSignUp.setOnClickListener {
            CommonClass.launchActivity(this, RegisterActivity::class.java)

        }
      }
}