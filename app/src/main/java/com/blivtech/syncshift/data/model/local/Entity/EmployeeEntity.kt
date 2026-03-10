package com.blivtech.syncshift.data.model.local.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.time.LocalDate

@Entity(tableName = "employee")
data class EmployeeEntity(
   val companyCode: String,
   @PrimaryKey
   val employeeCode: String,
   val employeeName: String,
   val address: String,
   val joiningDate: String,
   val dateOfBirth: String,
   val employeeType: String,
   val department: String,
   val designation: String,
   val mobileNumber: String,
   val email: String,
   val gender: String,
   val district: String,
   val taluk: String,
   val state: String,
   val mode: String,
   val salaryCode: String,
   val salaryType: String,
   val basicSalary: Double,
   val leaveCount: Int,
   val activeStatus: Int,
)