package com.blivtech.syncshift.ui.addEmployee

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.blivtech.syncshift.R
import com.blivtech.syncshift.data.model.Resource
import com.blivtech.syncshift.data.model.request.EmployeeRequest
import com.blivtech.syncshift.ui.components.ProgressDialog
import dagger.hilt.android.AndroidEntryPoint
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
        // Call API on button click
        findViewById<Button>(R.id.btnSubmit).setOnClickListener {

            val employee = EmployeeRequest(
                employee_id = "EMP001",
                bt_code = "BT1001",
                employee_name = "Saravanan",
                city = "Chennai",
                salary_type = "Monthly",
                salary_code = "1",
                email = "john@example.com",
                phone = "9876543210",
                department = "IT",
                designation = "Developer",
                date_of_birth = "1995-05-10",
                joining_date = "2024-11-01",
                address = "Chennai",
                pincode = "600001",
                status = "Active"
            )

            viewModel.addEmployee(employee)
        }
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
                    finish()
                }
            }
        }
    }
}
