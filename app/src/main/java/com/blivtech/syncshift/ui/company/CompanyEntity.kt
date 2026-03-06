package com.blivtech.syncshift.ui.company

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "company_master")
data class CompanyEntity(

    @PrimaryKey
    val companycode: String,

    val companyname: String,

    val towncode: String,

    val townname: String,

    val industry_type: String,

    val shift_details: String
)