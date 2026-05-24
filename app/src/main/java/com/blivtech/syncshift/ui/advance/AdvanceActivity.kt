package com.blivtech.syncshift.ui.advance

import android.app.DatePickerDialog
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.blivtech.syncshift.R
import com.blivtech.syncshift.databinding.ActivityAdvanceBinding
import com.blivtech.syncshift.ui.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.viewModels
import com.blivtech.syncshift.data.model.request.AdvanceRequest
import java.util.Calendar



@AndroidEntryPoint
class AdvanceActivity :BaseActivity() {

    private lateinit var binding: ActivityAdvanceBinding
    private var selectedFileUri: Uri? = null
    private val viewModel:AdvanceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding=ActivityAdvanceBinding.inflate(layoutInflater)

        setContentView(binding.root)


        clickListener()
        observeData()


    }

    private fun clickListener() {

        binding.btnSubmit.setOnClickListener {

            submitAdvance()
        }

        binding.btnSelectEmployee.setOnClickListener {

            Toast.makeText(

                this,

                "Select Employee Clicked",

                Toast.LENGTH_SHORT

            ).show()
        }
        binding.edtDate.setOnClickListener {

            showDatePicker()
        }
        binding.btnUploadDocuments.setOnClickListener {

            filePickerLauncher.launch("*/*")
        }
    }

    private fun submitAdvance() {

        val date =
            binding.edtDate.text.toString().trim()

        val amount =
            binding.edtAmount.text.toString().trim()

        val description =
            binding.edtDescription.text.toString().trim()

        if (date.isEmpty()) {

            binding.edtDate.error =
                "Select Date"

            return
        }

        if (amount.isEmpty()) {

            binding.edtAmount.error =
                "Enter Amount"

            return
        }
        val request = AdvanceRequest(

            btCode = "BT001",

            companyCode = "CMP001",

            employeeCode = "EMP001",

            employeeName = "Ajith",

            activityDate = date,

            amount = amount.toDouble(),

            paymentMode = "CASH",

            remarks = description
        )

        viewModel.saveAdvance(

            listOf(request)
        )
    } private fun observeData() {

        viewModel.responseLiveData.observe(this) {

            Toast.makeText(
                this,
                it,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    private fun showDatePicker() {

        val calendar = Calendar.getInstance()

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(

            this,

            { _, selectedYear, selectedMonth, selectedDay ->

                val date =

                    "$selectedYear-${
                        selectedMonth + 1
                    }-$selectedDay"

                binding.edtDate.setText(date)
            },

            year,
            month,
            day
        )

        datePickerDialog.show()
    }

    private val filePickerLauncher =

        registerForActivityResult(

            ActivityResultContracts.GetContent()

        ) { uri ->

            if (uri != null) {

                selectedFileUri = uri

                Toast.makeText(

                    this,

                    "File Selected",

                    Toast.LENGTH_SHORT

                ).show()
            }
        }


    }