package com.blivtech.syncshift.ui.addEmployee

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.blivtech.syncshift.R
import com.blivtech.syncshift.data.model.Resource
import com.blivtech.syncshift.data.model.request.EmployeeRequest
import com.blivtech.syncshift.ui.components.ProgressDialog
import com.blivtech.syncshift.utils.SharedPreferencesManager
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class AddEmployee : AppCompatActivity() {

    private val viewModel: EmployeeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_employee)

        observeViewModel()
        setupClick()
    }

    private fun setupClick() {
        val etName = findViewById<TextInputEditText>(R.id.etName)
        val etMobile = findViewById<TextInputEditText>(R.id.etMobile)
        val etEmail = findViewById<TextInputEditText>(R.id.etEmail)
        val etCity = findViewById<TextInputEditText>(R.id.etCity)
        val etDepartment =
            findViewById<MaterialAutoCompleteTextView>(R.id.etDepartment)
        val etDesignation =
            findViewById<MaterialAutoCompleteTextView>(R.id.etDesignation)

        val etDob = findViewById<TextInputEditText>(R.id.etDob)
        val etJoiningDate = findViewById<TextInputEditText>(R.id.etJoiningDate)
        val togglePay = findViewById<MaterialButtonToggleGroup>(R.id.togglePay)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)


        etDob.setOnClickListener {
            showDatePicker { date ->
                etDob.setText(date)
            }
        }


        etJoiningDate.setOnClickListener {
            showDatePicker { date ->
                etJoiningDate.setText(date)
            }
        }




        // Call API on button click
        btnSubmit.setOnClickListener {


            if (togglePay.checkedButtonId == -1) {
                Toast.makeText(
                    this,
                    "Please select salary type",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }


            val userdata=SharedPreferencesManager.getLoginData(this)
            val salaryType = when (togglePay.checkedButtonId) {
                R.id.btnDaily -> "Daily"
                R.id.btnWeekly -> "Weekly"
                R.id.btnMonthly -> "Monthly"
                else -> ""
            }



            val employee = EmployeeRequest(
                employee_id = "",
                bt_code = userdata.bt_code,
                employee_name = etName.text.toString(),
                city = etCity.text.toString(),
                salary_type = salaryType,
                salary_code = "",
                email = etEmail.text.toString(),
                phone = etMobile.text.toString(),
                department = etDepartment.text.toString(),
                designation = etDesignation.text.toString(),
                date_of_birth = etDob.text.toString(),
                joining_date = etJoiningDate.text.toString(),
                address = "erode",
                pincode = "600001",
                status = "Active"
            )
            viewModel.addEmployee(employee)
        }
    }



    private fun showDatePicker(onDateSelected: (String) -> Unit) {
        val cal = Calendar.getInstance()

        DatePickerDialog(
            this,
            { _, year, month, day ->
                val date =
                    String.format("%04d-%02d-%02d", year, month + 1, day)
                onDateSelected(date)
            },
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun observeViewModel() {
        val progress= ProgressDialog(this)

        viewModel.employeeState.observe(this) {
            when (it) {
                is Resource.Loading -> {
                    progress.show(this.window)
                }

                is Resource.Success -> {
                    progress.dismiss(this.window)
                    Toast.makeText(this, it.data?.message, Toast.LENGTH_SHORT).show()
                    finish()

                }

                is Resource.Error -> {
                    progress.dismiss(this.window)
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()

                }
            }
        }
    }
}
