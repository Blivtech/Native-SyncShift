package com.blivtech.syncshift.ui.company

import com.blivtech.syncshift.data.model.response.CompanyDetails

fun List<CompanyDetails>.toCompanyEntity(): List<CompanyEntity> =
    map {
        CompanyEntity(
            companycode = it.companyCode,
            companyname = it.companyName,
            towncode = it.townCode,
            townname = it.townName,
            industry_type = it.companyType,
            shift_details = ""
        )
    }