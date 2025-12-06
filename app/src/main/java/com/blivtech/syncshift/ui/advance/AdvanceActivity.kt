package com.blivtech.syncshift.ui.advance

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.blivtech.syncshift.databinding.ActivityAdvanceBinding
class AdvanceActivity :AppCompatActivity() {

    private lateinit var binding: ActivityAdvanceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityAdvanceBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}