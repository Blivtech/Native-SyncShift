package com.blivtech.syncshift.ui.company

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CompanyEntity")
data class CompanyEntity(
    @PrimaryKey
    val companyCode: String,
    val companyName: String,
    val townCode: String,
    val townName: String,
    val companyType: String,

)

