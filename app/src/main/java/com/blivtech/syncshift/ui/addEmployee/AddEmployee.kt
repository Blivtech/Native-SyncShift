package com.blivtech.syncshift.ui.addEmployee

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.blivtech.syncshift.databinding.ActivityAddEmployeeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddEmployee : AppCompatActivity() {

    private lateinit var binding: ActivityAddEmployeeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ Always create a fresh binding
        binding = ActivityAddEmployeeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
