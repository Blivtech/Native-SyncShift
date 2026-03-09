package com.blivtech.syncshift.ui.company

import com.blivtech.syncshift.data.model.response.CompanyDetails

fun List<CompanyDetails>.toListCompanyEntity(): List<CompanyEntity> =
    map {
        CompanyEntity(
            companyCode = it.companyCode,
            companyName = it.companyName,
            townCode = it.townCode,
            townName = it.townName,
            companyType = it.companyType,
            )
    }
