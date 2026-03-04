package com.blivtech.syncshift.data.model.response

data class LoginResponse(
    val btcode: String,
    val btname: String,
    val username: String,
    val mobileNumber: String,
    val usertype: String,
    val company_details: List<CompanyDetails> = emptyList())

data class CompanyDetails(
    val id: Long,
    val btcode: String,
    val companycode: String,
    val companyname: String,
    val towncode: String,
    val townname: String,
    val companytype: String,
    val createdAt: String
)

