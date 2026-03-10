package com.blivtech.syncshift.ui.addEmployee

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import com.blivtech.syncshift.R
import com.blivtech.syncshift.data.model.local.Entity.EmployeeEntity
import com.blivtech.syncshift.data.model.response.UiState
import com.blivtech.syncshift.data.model.request.EmployeeRequest
import com.blivtech.syncshift.ui.BaseActivity
import com.blivtech.syncshift.ui.components.ProgressDialog
import com.blivtech.syncshift.utils.CommonClass
import com.blivtech.syncshift.utils.SharedPreferencesManager
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class AddEmployee : BaseActivity() {

    private val viewModel: EmployeeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_employee)
        applyDisplayCutout(findViewById<TextInputEditText>(R.id.main))
        application
        observeViewModel()
        setupClick()
        val tittle = findViewById<TextView>(R.id.tv_tittle)
        tittle.text="Add Employee"

    }

    @SuppressLint("WrongViewCast")
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
        val etSalary = findViewById<TextInputEditText>(R.id.etSalary)
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


            val userdata= SharedPreferencesManager.getLoginData(this)
            val salaryType = when (togglePay.checkedButtonId) {
                R.id.btnDaily -> "Daily"
                R.id.btnWeekly -> "Weekly"
                R.id.btnMonthly -> "Monthly"
                else -> ""
            }

          val salary=etSalary.toString().toDoubleOrNull() ?: 0.0
          val companyCode=SharedPreferencesManager.getActiveCompanyCode(this)
            val employee = EmployeeEntity(
                employeeCode = "",
                companyCode = companyCode,
                employeeName = etName.text.toString(),
                address = etCity.text.toString(),
                email = etEmail.text.toString(),
                mobileNumber = etMobile.text.toString(),
                department = etDepartment.text.toString(),
                designation = etDesignation.text.toString(),
                dateOfBirth = etDob.text.toString(),
                joiningDate = etJoiningDate.text.toString(),
                gender = "Male",
                district = "Erode",
                taluk = "Bhavani",
                state = "TamilNadu",
                mode = "Android-App",
                basicSalary = salary,
                salaryType = salaryType,
                salaryCode = "",
                leaveCount = 0,
                employeeType = "permanent",
                activeStatus = 1,
            )
            viewModel.addEmployee(employee)
        }
    }


    private fun observeViewModel() {
        val progress= ProgressDialog(this)

        viewModel.employeeState.observe(this) {
            when (it) {
                is UiState.Loading -> {
                    progress.show(this.window)
                }
                is UiState.Success -> {
                    progress.dismiss(this.window)
                    showToast(it.message)
                    finish()
                }
                is UiState.Error -> {
                    progress.dismiss(this.window)
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
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


    fun showToast(msg:String){
        CommonClass.showToast(this,msg)
    }

}
