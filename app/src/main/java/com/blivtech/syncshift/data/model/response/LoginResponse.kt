package com.blivtech.syncshift.data.model.response

import com.blivtech.syncshift.ui.company.ShiftEntity

data class LoginResponse(
    val btCode: String,
    val btName: String,
    val userName: String,
    val mobileNumber: String,
    val userType: String,
    val companyDetails: List<CompanyDetails> = emptyList())

data class CompanyDetails(
    val id: Long,
    val btCode: String,
    val companyCode: String,
    val companyName: String,
    val townCode: String,
    val townName: String,
    val companyType: String,
    val shiftDetails: List<ShiftEntity> = emptyList())
