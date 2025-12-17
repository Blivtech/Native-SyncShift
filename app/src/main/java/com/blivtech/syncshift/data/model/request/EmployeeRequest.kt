package com.blivtech.syncshift.data.model.request

data class EmployeeRequest(
    val employee_id: String,
    val bt_code: String,
    val employee_name: String,
    val city: String,
    val salary_type: String,
    val salary_code: String,
    val email: String,
    val phone: String,
    val department: String,
    val designation: String,
    val date_of_birth: String,
    val joining_date: String,
    val address: String,
    val pincode: String,
    val status: String
)
